<!---
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

## Default Cache Behavior

A `maven-build-cache-config.xml` file is **not required**. When no configuration file is present,
the extension starts with sensible built-in defaults and applies automatic plugin parameter
reconciliation from a bundled plugin catalog. This page describes exactly what those defaults are,
what the built-in catalog covers, and where the limits of zero-config operation lie.

---

### General Defaults

| Setting                      | Default value                                               | When to consider changing                                                                                                          |
|------------------------------|-------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| Cache enabled                | `true`                                                      | Disable temporarily (`-Dmaven.build.cache.enabled=false`) to rule out cache issues during debugging                                |
| Hash algorithm               | `XX` (xxHash — fast, non-cryptographic)                     | Switch to `SHA-256` or `SHA-384` for stronger integrity guarantees, e.g. when sharing via a remote cache in regulated environments |
| Local cache location         | `build-cache/` directory, sibling to the local Maven repo   | Redirect to a faster disk (SSD/RAM disk) or a shared network path for team-local sharing                                           |
| Max local builds per project | `3`                                                         | Increase when frequently switching between branches; decrease to limit disk usage on constrained machines                          |
| Compile-phase caching        | `true` — `compile` and `test-compile` are cached            | Set to `false` if you want to cache only `package`-phase and later outputs (avoids caching partial/incremental compile results)    |
| Restore generated sources    | `true`                                                      | Disable in CI pipelines where generation is fast and downloading cached sources adds more overhead than recomputing them           |
| Restore on-disk artifacts    | `true`                                                      | Disable when cached on-disk artifacts are not consumed by subsequent steps and restoring them wastes I/O                           |
| Lazy artifact restore        | `false` — all cached artifacts resolved eagerly             | Enable (`-Dmaven.build.cache.lazyRestore=true`) in large multi-module builds to defer artifact downloads until actually needed     |
| Remote cache                 | Disabled                                                    | Enable to share build results across CI agents or developer machines; requires a `<remote>` section in the config file             |
| Plugin introspection         | `true` — editable mojo parameters are recorded in buildinfo | Rarely changed; set to `false` only if parameter recording causes performance problems with a very large number of plugins         |

---

### Automatic Plugin Parameter Reconciliation

The extension bundles a **plugin catalog** — a set of XML descriptors that classify every known
plugin parameter as either _functional_ or _behavioral_:

* **Functional** parameters affect what the plugin produces (bytecode, generated sources, packaged
  artifacts). A change to any functional parameter invalidates the cache for that execution.
* **Behavioral** parameters affect _how_ the plugin runs (verbosity, forking, memory) but not the
  output. Changes to behavioral parameters do **not** invalidate the cache; they are recorded in
  `buildinfo.xml` for diagnostics only.
* **Skip properties** (e.g. `skipMain`, `maven.test.skip`) are a special case: the cache is
  considered a hit when the skip flag is set, allowing downstream modules to be restored without
  re-running the skipped execution.

When a plugin and goal are found in the catalog, reconciliation happens **automatically** — no
`<executionControl><reconcile>` block is needed in the config file.

#### Unknown plugins, goals, and parameters

The `onUnknownPlugin` attribute on `<executionControl><reconcile>` (default: `fail`) governs
what happens whenever the extension cannot find reconciliation rules for a mojo execution. This
covers three distinct situations:

* A **plugin** is executed that has no catalog entry and no explicit `<plugin>` block in `<reconcile>`.
* A **goal** is executed whose plugin is catalogued but the specific goal is not listed in the catalog.
* A **parameter** is present in the mojo descriptor for a catalogued goal but is absent from the
  catalog entry for that goal — meaning neither its category nor its skip-property classification
  is known.

In all three cases the same action is applied:

| Value       | Behavior                                                                               |
|-------------|----------------------------------------------------------------------------------------|
| `fail`      | The build fails with an error listing the uncatalogued plugin/goal/parameter (default) |
| `printWarn` | A warning is logged and the build continues; the uncatalogued element is skipped       |
| `ignore`    | The build continues silently; the uncatalogued element is skipped                      |

Per-plugin opt-out is also available via `skipReconciliation="true"` on an individual
`<plugin>` entry inside `<reconcile>`, which suppresses reconciliation entirely for that plugin.

---

### Known Plugins

The authoritative list of bundled plugin catalog descriptors is maintained in
[
`src/main/resources/META-INF/maven-build-cache-extension/plugins/index.txt`](https://github.com/apache/maven-build-cache-extension/tree/master/src/main/resources/META-INF/maven-build-cache-extension/plugins/index.txt).
Each line in that file names a catalog XML descriptor packaged inside the extension JAR under the
same directory. At runtime the extension loads every descriptor listed there. The tables below
document the parameter classification for each currently bundled plugin.

All other plugins require either an explicit `<reconcile>` configuration or a permissive
`onUnknownPlugin` setting.

---

### Limitations

Zero-config operation has the following limitations:

1. **Only catalog-listed plugins get automatic reconciliation.** Any plugin not present in the
   bundled catalog will cause a build failure by default (`onUnknownPlugin=fail`). This is
   intentional — it forces you to make an explicit decision about uncatalogued plugins rather than
   silently producing incorrect cache hits.

2. **The catalog covers a single version range per plugin.** If your project uses a plugin version
   outside the declared range, behavior may not be accurate. Override with explicit `<reconcile>`
   configuration if needed.

3. **Environment-specific parameters are compared after path normalization.** Parameters marked
   `envSpecific` in the catalog (such as `outputDirectory`, `executable`) have their string values
   normalized relative to the project base directory before comparison, so absolute paths on
   different machines resolve correctly. This normalization is best-effort; unusual path formats
   may still cause spurious mismatches.

4. **The catalog is bundled inside the extension JAR.** It reflects plugin behavior at the time
   the extension was released. Newly introduced plugin parameters will be absent until the
   extension is updated or you add them via explicit `<reconcile>` configuration.

5. **Remote cache is not enabled by default.** A `<remote>` section must be added explicitly.

---

### Summary

Out-of-the-box, the extension caches local builds using xxHash, stores up to three builds per
module, and automatically reconciles parameters for the plugins listed above — no configuration
file required.

When you need to go further:

* **Tune input fingerprinting** (file globs, source roots, excluded files) — see [How-To](how-to.html)
* **Add reconciliation for uncatalogued plugins** — see [How-To](how-to.html) and the sample
  [maven-build-cache-config.xml](maven-build-cache-config.xml)
* **Configure remote cache sharing** — see [Remote Cache Setup](remote-cache.html)
* **Adjust command-line flags and XML config attributes** — see [Cache Parameters](parameters.html)
* **Understand how the cache key is computed** — see [Concepts](concepts.html)
