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

import java.util.List;

import org.apache.maven.buildcache.plugincatalog.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link PluginCatalogRegistry} and {@link ParameterCategory}.
 */
class PluginCatalogRegistryTest {

    private static final String GROUP_ID = "org.apache.maven.plugins";
    private static final String ARTIFACT_ID = "maven-compiler-plugin";
    private static final String GOAL_COMPILE = "compile";

    private PluginCatalogRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new PluginCatalogRegistry();
    }

    @Test
    void catalogIsLoadedForCompilerPlugin() {
        assertTrue(
                registry.hasCatalog(GROUP_ID, ARTIFACT_ID),
                "Compiler plugin catalog should be bundled on the classpath");
    }

    @Test
    void functionalParameterSourceIsTracked() {
        Parameter source = registry.findParameter(GROUP_ID, ARTIFACT_ID, GOAL_COMPILE, "source");
        assertNotNull(source, "'source' must be in the catalog");
        assertEquals(
                ParameterCategory.FUNCTIONAL,
                ParameterCategory.of(source.getCategory()),
                "'source' must be classified as FUNCTIONAL");
        assertFalse(source.isSkipProperty());
    }

    @Test
    void behavioralParameterVerboseIsNotTracked() {
        Parameter verbose = registry.findParameter(GROUP_ID, ARTIFACT_ID, GOAL_COMPILE, "verbose");
        assertNotNull(verbose, "'verbose' must be in the catalog");
        assertEquals(
                ParameterCategory.BEHAVIORAL,
                ParameterCategory.of(verbose.getCategory()),
                "'verbose' must be classified as BEHAVIORAL");
        assertFalse(verbose.isSkipProperty());
    }

    @Test
    void skipParameterSkipMainHasSkipPropertyFlag() {
        Parameter skipMain = registry.findParameter(GROUP_ID, ARTIFACT_ID, GOAL_COMPILE, "skipMain");
        assertNotNull(skipMain, "'skipMain' must be in the catalog");
        assertTrue(skipMain.isSkipProperty(), "'skipMain' must have skipProperty=true");
    }

    @Test
    void unknownPluginReturnsNoCatalog() {
        assertFalse(registry.hasCatalog("com.example", "unknown-plugin"));
    }

    @Test
    void unknownParameterReturnsNull() {
        assertNull(registry.findParameter(GROUP_ID, ARTIFACT_ID, GOAL_COMPILE, "nonExistentParam"));
    }

    @Test
    void getParametersReturnsNonEmptyListForKnownGoal() {
        List<Parameter> params = registry.getParameters(GROUP_ID, ARTIFACT_ID, GOAL_COMPILE);
        assertFalse(params.isEmpty(), "compile goal must have at least one parameter in catalog");
    }

    @Test
    void getParametersReturnsEmptyListForUnknownGoal() {
        List<Parameter> params = registry.getParameters(GROUP_ID, ARTIFACT_ID, "nonExistentGoal");
        assertTrue(params.isEmpty());
    }

    @Test
    void hasGoalReturnsTrueForKnownGoal() {
        assertTrue(registry.hasGoal(GROUP_ID, ARTIFACT_ID, GOAL_COMPILE));
    }

    @Test
    void hasGoalReturnsFalseForUnknownGoal() {
        assertFalse(registry.hasGoal(GROUP_ID, ARTIFACT_ID, "nonExistentGoal"));
    }

    @Test
    void hasGoalReturnsFalseForUnknownPlugin() {
        assertFalse(registry.hasGoal("com.example", "unknown-plugin", GOAL_COMPILE));
    }

    // -------------------------------------------------------------------------
    // ParameterCategory enum tests
    // -------------------------------------------------------------------------

    @Test
    void parameterCategoryOfFunctional() {
        assertEquals(ParameterCategory.FUNCTIONAL, ParameterCategory.of("functional"));
        assertEquals(ParameterCategory.FUNCTIONAL, ParameterCategory.of("FUNCTIONAL"));
        assertEquals(ParameterCategory.FUNCTIONAL, ParameterCategory.of("Functional"));
    }

    @Test
    void parameterCategoryOfBehavioral() {
        assertEquals(ParameterCategory.BEHAVIORAL, ParameterCategory.of("behavioral"));
        assertEquals(ParameterCategory.BEHAVIORAL, ParameterCategory.of("BEHAVIORAL"));
    }

    @Test
    void parameterCategoryOfNullReturnsNull() {
        assertNull(ParameterCategory.of(null));
    }

    @Test
    void parameterCategoryOfUnrecognisedReturnsNull() {
        assertNull(ParameterCategory.of("unknown"));
    }
}
