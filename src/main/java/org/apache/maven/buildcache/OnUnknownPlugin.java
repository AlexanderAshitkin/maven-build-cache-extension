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

/**
 * Action taken by the build cache extension when a plugin, plugin-goal, or plugin-goal-parameter
 * is encountered that has no entry in the bundled plugin catalog and no explicit
 * {@code <plugin>} reconcile configuration in {@code .mvn/maven-build-cache-config.xml}.
 *
 * <p>Configured via the {@code onUnknownPlugin} attribute of the
 * {@code <executionControl><reconcile>} element.
 */
public enum OnUnknownPlugin {

    /**
     * Fail the build with an {@link IllegalStateException} (default).
     * Forces users to make an explicit deterministic choice for every plugin.
     */
    FAIL,

    /**
     * Silently skip reconciliation for the unknown plugin / goal / parameter.
     * The cache key will not include any properties for this execution.
     */
    IGNORE,

    /**
     * Log a {@code WARN}-level message and continue without reconciliation,
     * as if {@link #IGNORE} had been set.
     */
    PRINT_WARN;

    /**
     * Returns the {@link OnUnknownPlugin} matching the given string value (case-insensitive).
     *
     * @param value the raw string from configuration; may be {@code null}
     * @return the matching constant, or {@code null} when unrecognised
     */
    public static OnUnknownPlugin of(String value) {
        if (value == null) {
            return null;
        }
        for (OnUnknownPlugin v : values()) {
            if (v.name().equalsIgnoreCase(value)) {
                return v;
            }
        }
        return null;
    }
}
