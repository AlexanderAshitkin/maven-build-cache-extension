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
package org.apache.maven.buildcache;

import javax.inject.Named;
import javax.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.buildcache.plugincatalog.Goal;
import org.apache.maven.buildcache.plugincatalog.Parameter;
import org.apache.maven.buildcache.plugincatalog.PluginCatalog;
import org.apache.maven.buildcache.plugincatalog.io.xpp3.PluginCatalogXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads and indexes plugin parameter catalogs bundled under
 * {@code META-INF/maven-build-cache-extension/plugins/} on the classpath.
 *
 * <p>The registry is a singleton; catalogs are loaded once at construction time.
 */
@Named
@Singleton
public class PluginCatalogRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginCatalogRegistry.class);

    private static final String INDEX = "META-INF/maven-build-cache-extension/plugins/index.txt";
    private static final String DIR = "META-INF/maven-build-cache-extension/plugins/";

    /** key: "groupId:artifactId" → catalog */
    private final Map<String, PluginCatalog> catalogs;

    public PluginCatalogRegistry() {
        this.catalogs = loadCatalogs();
    }

    /**
     * Returns the parameters declared for a specific goal, or an empty list if
     * no catalog entry exists for this plugin / goal combination.
     */
    public List<Parameter> getParameters(String groupId, String artifactId, String goal) {
        PluginCatalog catalog = catalogs.get(key(groupId, artifactId));
        if (catalog == null) {
            return Collections.emptyList();
        }
        for (Goal g : catalog.getGoals()) {
            if (goal.equals(g.getName())) {
                return g.getParameters();
            }
        }
        return Collections.emptyList();
    }

    /**
     * Returns {@code true} when a catalog exists for the given plugin coordinates.
     */
    public boolean hasCatalog(String groupId, String artifactId) {
        return catalogs.containsKey(key(groupId, artifactId));
    }

    /**
     * Returns {@code true} when the catalog for the given plugin contains an entry for
     * the specified goal. Returns {@code false} when the plugin is not catalogued at all.
     */
    public boolean hasGoal(String groupId, String artifactId, String goal) {
        PluginCatalog catalog = catalogs.get(key(groupId, artifactId));
        if (catalog == null) {
            return false;
        }
        for (Goal g : catalog.getGoals()) {
            if (goal.equals(g.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the {@link Parameter} descriptor for the given parameter name, or
     * {@code null} if the plugin or goal is not catalogued, or the parameter is not listed.
     */
    public Parameter findParameter(String groupId, String artifactId, String goal, String name) {
        for (Parameter p : getParameters(groupId, artifactId, goal)) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private Map<String, PluginCatalog> loadCatalogs() {
        Map<String, PluginCatalog> result = new HashMap<>();
        ClassLoader cl = getClass().getClassLoader();

        InputStream indexStream = cl.getResourceAsStream(INDEX);
        if (indexStream == null) {
            LOGGER.debug("Plugin catalog index not found at {}, registry is empty", INDEX);
            return Collections.emptyMap();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(indexStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String resourcePath = DIR + line;
                InputStream catalogStream = cl.getResourceAsStream(resourcePath);
                if (catalogStream == null) {
                    LOGGER.warn("Plugin catalog listed in index but not found on classpath: {}", resourcePath);
                    continue;
                }
                try {
                    PluginCatalog catalog = new PluginCatalogXpp3Reader().read(catalogStream);
                    String catalogKey = key(catalog.getGroupId(), catalog.getArtifactId());
                    result.put(catalogKey, catalog);
                    LOGGER.debug(
                            "Loaded plugin catalog for {}:{} ({} goals)",
                            catalog.getGroupId(),
                            catalog.getArtifactId(),
                            catalog.getGoals().size());
                } catch (IOException | XmlPullParserException e) {
                    LOGGER.warn("Failed to parse plugin catalog {}: {}", resourcePath, e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to read plugin catalog index: {}", e.getMessage());
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(result);
    }

    private static String key(String groupId, String artifactId) {
        return groupId + ":" + artifactId;
    }
}
