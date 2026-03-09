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
 * Category of a plugin parameter as declared in the build cache plugin catalog.
 *
 * <ul>
 *   <li>{@link #FUNCTIONAL} – parameter affects the build output; a value change invalidates the cache.</li>
 *   <li>{@link #BEHAVIORAL} – parameter affects only execution behaviour (e.g. verbosity, parallelism);
 *       value changes are ignored for cache key purposes.</li>
 * </ul>
 */
public enum ParameterCategory {
    FUNCTIONAL,
    BEHAVIORAL;

    /**
     * Returns the {@code ParameterCategory} matching the given string value (case-insensitive).
     *
     * @param value the raw category string from the plugin catalog XML; may be {@code null}
     * @return the matching category, or {@code null} when {@code value} is {@code null} or unrecognised
     */
    public static ParameterCategory of(String value) {
        if (value == null) {
            return null;
        }
        for (ParameterCategory c : values()) {
            if (c.name().equalsIgnoreCase(value)) {
                return c;
            }
        }
        return null;
    }
}
