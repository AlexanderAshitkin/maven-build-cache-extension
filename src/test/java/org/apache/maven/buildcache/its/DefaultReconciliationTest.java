/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.buildcache.its;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.it.Verifier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import static org.apache.maven.buildcache.its.CacheITUtils.CACHE_HIT;
import static org.apache.maven.buildcache.its.CacheITUtils.CACHE_SAVED;
import static org.apache.maven.buildcache.its.CacheITUtils.replaceInFile;

/**
 * Integration tests verifying automatic default reconciliation driven by the plugin catalog registry.
 *
 * <p>The test project uses {@code maven-compiler-plugin} with no explicit {@code <reconcile>}
 * configuration in the cache config. The extension should automatically:
 * <ul>
 *   <li>Invalidate the cache when <b>functional</b> parameters change (e.g. {@code source}).</li>
 *   <li>Keep the cache when only <b>behavioral</b> parameters change (e.g. {@code verbose}).</li>
 *   <li>Allow a cache hit when a skip flag is set (skip-property semantics).</li>
 * </ul>
 */
@ResourceLock(Resources.SYSTEM_PROPERTIES)
class DefaultReconciliationTest {

    private static final Path PROJECT_DIR =
            Paths.get("src/test/projects/default-reconciliation").toAbsolutePath();

    @BeforeAll
    static void setUpMaven() throws Exception {
        MavenSetup.configureMavenHome();
    }

    /**
     * DR-01: Changing a <b>behavioral</b> parameter ({@code verbose}) must not invalidate the cache.
     *
     * <p>Build 1: compile with {@code verbose=false} → cache saved.
     * <p>Build 2: compile with {@code verbose=true} → cache hit (behavioral change ignored).
     */
    @Test
    void behavioralChangeDoesNotInvalidateCache() throws Exception {
        Verifier verifier = ReferenceProjectBootstrap.prepareProject(PROJECT_DIR, "DR-01");
        verifier.setAutoclean(false);

        // Build 1: cold cache
        verifier.setLogFileName("../log-1.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);

        // Change verbose (behavioral) in pom.xml
        Path pom = Paths.get(verifier.getBasedir()).resolve("pom.xml");
        replaceInFile(pom, "<verbose>false</verbose>", "<verbose>true</verbose>");

        // Build 2: behavioral change → still a cache hit
        verifier.setLogFileName("../log-2.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_HIT);
    }

    /**
     * DR-02: Changing a <b>functional</b> parameter ({@code source}) must invalidate the cache.
     *
     * <p>Build 1: compile with {@code source=8} → cache saved.
     * <p>Build 2: compile with {@code source=11} → cache miss, new entry saved.
     */
    @Test
    void functionalChangeInvalidatesCache() throws Exception {
        Verifier verifier = ReferenceProjectBootstrap.prepareProject(PROJECT_DIR, "DR-02");
        verifier.setAutoclean(false);

        // Build 1: cold cache
        verifier.setLogFileName("../log-1.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);

        // Change source (functional) in pom.xml
        Path pom = Paths.get(verifier.getBasedir()).resolve("pom.xml");
        replaceInFile(pom, "<source>8</source>", "<source>11</source>");
        replaceInFile(pom, "<target>8</target>", "<target>11</target>");

        // Build 2: functional change → cache miss, new entry saved
        verifier.setLogFileName("../log-2.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);
    }

    /**
     * DR-03: Setting a skip property ({@code maven.main.skip=true}) must allow a cache hit
     * against a build where compilation ran.
     *
     * <p>Build 1: compile normally → {@code skipMain=false} → cache saved.
     * <p>Build 2: compile with {@code -Dmaven.main.skip=true} → skip detected, cache hit with warning.
     */
    @Test
    void skipPropertyAllowsCacheHit() throws Exception {
        Verifier verifier = ReferenceProjectBootstrap.prepareProject(PROJECT_DIR, "DR-03");
        verifier.setAutoclean(false);

        // Build 1: cold cache, compilation runs normally
        verifier.setLogFileName("../log-1.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);

        // Build 2: skip main compilation → skip-property semantics → cache hit
        verifier.addCliOption("-Dmaven.main.skip=true");
        verifier.setLogFileName("../log-2.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_HIT);
    }

    /**
     * DR-04: Explicit {@code <reconcile>} adds tracking for a catalog-behavioral parameter
     * on top of catalog defaults, forming a <b>superset</b>.
     *
     * <p>The catalog classifies {@code verbose} as behavioral — changes are normally ignored
     * (demonstrated by DR-01). When an explicit {@code <reconcile>} entry adds {@code verbose}
     * to the tracked-property list, it is merged on top of the catalog-derived functional
     * parameters. The combined set is a superset of catalog defaults, and changes to the
     * explicitly-added parameter now invalidate the cache.
     *
     * <p>Build 1: compile normally → cache saved with {@code verbose=false} recorded.
     * <p>Build 2: compile with {@code -Dmaven.compiler.verbose=true} → cache miss,
     *    because {@code verbose} is now tracked via the explicit reconcile entry.
     */
    @Test
    void explicitReconcileFormsSupersetOfCatalogDefaults() throws Exception {
        Verifier verifier = ReferenceProjectBootstrap.prepareProject(PROJECT_DIR, "DR-04");
        verifier.setAutoclean(false);

        // Inject explicit reconcile: add verbose to tracked params on top of catalog defaults
        Path cacheConfig = Paths.get(verifier.getBasedir()).resolve(".mvn/maven-build-cache-config.xml");
        replaceInFile(
                cacheConfig,
                "</cache>",
                "    <executionControl>\n"
                        + "        <reconcile>\n"
                        + "            <plugins>\n"
                        + "                <plugin artifactId=\"maven-compiler-plugin\" goal=\"compile\">\n"
                        + "                    <reconciles>\n"
                        + "                        <reconcile propertyName=\"verbose\" defaultValue=\"false\"/>\n"
                        + "                    </reconciles>\n"
                        + "                </plugin>\n"
                        + "            </plugins>\n"
                        + "        </reconcile>\n"
                        + "    </executionControl>\n"
                        + "</cache>");

        // Build 1: cold cache — verbose=false stored in buildinfo as tracked
        verifier.setLogFileName("../log-1.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);

        // Build 2: override verbose via CLI — now tracked, value differs → cache miss
        verifier.addCliOption("-Dmaven.compiler.verbose=true");
        verifier.setLogFileName("../log-2.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);
    }

    /**
     * DR-05: An explicit {@code <reconcile>} entry <b>overrides</b> the catalog's TrackedProperty
     * configuration for the same parameter.
     *
     * <p>The catalog tracks {@code debug} as a functional parameter with no skip semantics.
     * The explicit reconcile entry overrides it by setting {@code skipValue="false"}: when the
     * current value of {@code debug} equals {@code false}, the extension treats this as a
     * "skip-like" condition and returns a cache hit (with a warning) against a previously cached
     * build where {@code debug=true}. This override would not be possible without the explicit
     * reconcile entry — without it the catalog-derived TrackedProperty has no skipValue, and the
     * change from {@code true} to {@code false} would produce a cache miss.
     *
     * <p>Build 1: compile with {@code debug=true} (mojo default) → cache saved.
     * <p>Build 2: compile with {@code -Dmaven.compiler.debug=false} → cache hit,
     *    because the explicit reconcile override supplies {@code skipValue="false"} which
     *    matches the current value and triggers skip-value semantics.
     */
    @Test
    void explicitReconcileOverridesCatalogTrackedProperty() throws Exception {
        Verifier verifier = ReferenceProjectBootstrap.prepareProject(PROJECT_DIR, "DR-05");
        verifier.setAutoclean(false);

        // Inject explicit reconcile: override debug — add skipValue=false on top of catalog definition
        Path cacheConfig = Paths.get(verifier.getBasedir()).resolve(".mvn/maven-build-cache-config.xml");
        replaceInFile(
                cacheConfig,
                "</cache>",
                "    <executionControl>\n"
                        + "        <reconcile>\n"
                        + "            <plugins>\n"
                        + "                <plugin artifactId=\"maven-compiler-plugin\" goal=\"compile\">\n"
                        + "                    <reconciles>\n"
                        + "                        <reconcile propertyName=\"debug\" defaultValue=\"true\""
                        + " skipValue=\"false\"/>\n"
                        + "                    </reconciles>\n"
                        + "                </plugin>\n"
                        + "            </plugins>\n"
                        + "        </reconcile>\n"
                        + "    </executionControl>\n"
                        + "</cache>");

        // Build 1: cold cache — debug=true (mojo default) stored in buildinfo
        verifier.setLogFileName("../log-1.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);

        // Build 2: override debug=false via CLI
        //   expected="true" (from buildinfo), current="false" → mismatch
        //   but current == skipValue ("false") → skip-value semantics → cache hit with warning
        verifier.addCliOption("-Dmaven.compiler.debug=false");
        verifier.setLogFileName("../log-2.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_HIT);
    }

    /**
     * DR-06: An explicit reconcile entry with {@code skipReconciliation="true"} completely
     * <b>disables</b> catalog-driven reconciliation for that plugin goal.
     *
     * <p>When {@code skipReconciliation=true} is set on the plugin reconcile element, no
     * parameters — not even catalog-functional ones — are tracked in the cache key.
     * {@link org.apache.maven.buildcache.xml.CacheConfigImpl#getTrackedProperties} returns an
     * empty list for that goal, so {@code isParamsMatched()} always passes, and any parameter
     * change leaves the cache intact.
     *
     * <p>Build 1: compile normally with {@code source=8} → cache saved (no params tracked).
     * <p>Build 2: compile with {@code -Dmaven.compiler.source=11} → cache hit, because
     *    {@code source} is a functional parameter that would normally invalidate the cache
     *    (see DR-02) but is ignored when {@code skipReconciliation=true}.
     */
    @Test
    void skipReconciliationDisablesCatalogTracking() throws Exception {
        Verifier verifier = ReferenceProjectBootstrap.prepareProject(PROJECT_DIR, "DR-06");
        verifier.setAutoclean(false);

        // Inject skipReconciliation=true: disables all catalog tracking for compiler:compile
        Path cacheConfig = Paths.get(verifier.getBasedir()).resolve(".mvn/maven-build-cache-config.xml");
        replaceInFile(
                cacheConfig,
                "</cache>",
                "    <executionControl>\n"
                        + "        <reconcile>\n"
                        + "            <plugins>\n"
                        + "                <plugin artifactId=\"maven-compiler-plugin\" goal=\"compile\""
                        + " skipReconciliation=\"true\"/>\n"
                        + "            </plugins>\n"
                        + "        </reconcile>\n"
                        + "    </executionControl>\n"
                        + "</cache>");

        // Build 1: cold cache — no tracked params, cache saved with source=8
        verifier.setLogFileName("../log-1.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_SAVED);

        // Build 2: change source to 11 via CLI (would normally miss — see DR-02)
        //   With skipReconciliation=true: tracked list is empty → isParamsMatched always passes
        //   → cache hit despite the functional parameter change
        verifier.addCliOption("-Dmaven.compiler.source=11");
        verifier.setLogFileName("../log-2.txt");
        verifier.executeGoal("compile");
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog(CACHE_HIT);
    }
}
