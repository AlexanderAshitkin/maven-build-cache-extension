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
# Plugin Parameter Classification Guide

This document defines the authoritative rules for classifying plugin parameters in the
maven-build-cache-extension plugin catalog. Every parameter in every catalog XML file under
`src/main/resources/META-INF/maven-build-cache-extension/plugins/` must be assigned values
for three independent attributes: **`category`**, **`envSpecific`**, and **`skipProperty`**.

Consistent classification is critical: an error here silently produces incorrect cache hits
(a `behavioral` parameter that should be `functional`) or unnecessary cache misses (the reverse).

---

## Why Classification Matters

The build cache extension uses the catalog to decide, for each mojo execution:

| Attribute | Effect on caching |
|-----------|-------------------|
| `category=functional` | Parameter is included in the **cache key**. Any change invalidates the cache and forces a rebuild. |
| `category=behavioral` | Parameter is **recorded** in `buildinfo.xml` for diagnostics but excluded from the cache key. Changes do not invalidate the cache. |
| `envSpecific=true` | The parameter's string value is **path-normalized** relative to the project base directory before being hashed into the cache key. |
| `envSpecific=false` | The value is hashed as-is. |
| `skipProperty=true` | When this parameter is set to its skip value (usually `true`), the entire goal execution is skipped and a **cache hit is returned** for the module without re-running the goal. |

---

## 1. `category`: functional vs behavioral

### Rule

> **Functional** — the parameter affects _what_ the plugin produces.
> **Behavioral** — the parameter affects _how_ the plugin runs, but the output is identical
> regardless of its value.

### Decision question

> *"If two developers with identical source code set this parameter to different values,
> could the build output differ?"*
>
> **Yes → `functional`.   No → `behavioral`.**

### Functional parameters

Assign `functional` when the parameter controls any of the following:

- **Output content** — which files are included/excluded, which classes are compiled, which
  resources are filtered, which APIs are generated, which report data is collected.
- **Output format or structure** — file encoding, archive format, manifest entries, report
  format (XML, HTML, JSON), bytecode target version.
- **Invoked tool configuration** — compiler flags that change bytecode (`-source`, `-target`,
  `-release`, `-g`, `-parameters`), static analysis rule sets, database migration scripts.
- **Artifact identity** — `finalName`, `classifier`, version strings embedded in the output.
- **Reproducibility attributes** — `outputTimestamp`, anything that affects binary-identical
  reproducible builds.
- **Skip-like parameters that alter the output set** — e.g. `generateClient`, `includeTests`,
  `skinnyWars`.

#### Common functional parameters (examples)

| Parameter | Why |
|-----------|-----|
| `encoding` | Determines which bytes are written to output files |
| `source`, `target`, `release` | Determines bytecode version |
| `includes`, `excludes` | Determines which files appear in the artifact |
| `outputDirectory` | Where artifacts land (also `envSpecific`) |
| `archive` / manifest entries | Directly embedded in the output JAR |
| `configLocation` (Checkstyle) | Determines which rules are checked |
| `format` (reports) | Changes the output file type |
| `outputTimestamp` | Controls reproducible-build timestamps |

### Behavioral parameters

Assign `behavioral` when the parameter controls any of the following:

- **Verbosity and logging** — `verbose`, `debug`, `quiet`, `showWarnings`, `showDeprecation`,
  `logViolationsToConsole`, `printResultSet`.
- **Parallelism and forking** — `fork`, `threads`, `maxForks`, `forkCount`, `parallelThreads`.
- **JVM resources for the plugin itself** — `meminitial`, `maxmem`, `jvmArgs` when used only
  to size the forked JVM, not to pass compiler flags to `javac`.
- **Incremental/up-to-date checks** — `staleMillis`, `useIncrementalCompilation`,
  `recompileMode`, `upToDateChecking`. These affect _when_ the plugin runs, not _what_ it
  produces when it does.
- **Failure thresholds** — `failOnError`, `failOnWarning`, `failBuildOnCVSS`,
  `maxAllowedViolations`. These control whether the build fails but not what artifacts are
  produced.
- **Timeouts** — `timeout`, `connectionTimeout`, `nativeGitTimeoutInMs`.
- **Compiler/tool reuse strategy** — `compilerReuseStrategy`.

> **Gray area: `failOnError` and similar.**
> These parameters do not change the artifact but they do change observable build behavior.
> Treat them as `behavioral`. If a team changes `failOnError=false` to `failOnError=true`,
> the artifact is the same; only whether the build exits cleanly differs.

### Edge cases

#### Parameters that are both behavioral and functional

Some parameters have a dual nature depending on context. Use the dominant effect:

- `fork=true` for the compiler plugin: the compiled bytecode is identical; only the JVM
  process model changes → **behavioral**.
- `fork=true` for the surefire plugin combined with `argLine`: the JVM arguments passed to
  tests could affect test outcomes → `argLine` is **functional** (and `envSpecific=true`).

#### `showCompilationChanges`, `skipMultiThreadWarning`, `debugFileName`

These only produce console output or debug files consumed by developers, never by downstream
build steps → **behavioral**.

#### `outputDirectory` in report plugins

Even though changing it never changes the _content_ of the report, it changes _where_ the
report is written and whether downstream goals find it → **functional** (and `envSpecific=true`).

---

## 2. `envSpecific`: true vs false

### Rule

A parameter is `envSpecific` when its value can legitimately differ between two developers
with identical source code because of machine-local conditions (file system layout, installed
tools, registries). The extension path-normalizes such values before hashing.

A parameter is **not** `envSpecific` when the same string or number produces identical
behavior on any standard Maven installation running the same project.

### The three `envSpecific=true` triggers

Any one of the following is sufficient:

---

#### Trigger 1 — Structural path

> The declared Java type is `File`, `Path`, or a `String`/`URL` whose documented purpose is
> to carry an absolute or project-relative file system path.

The value is inherently environment-rooted: `/home/alice/project` vs `/home/bob/project`
refer to the same project but differ lexically.

**Examples:** `outputDirectory`, `sourceDirectory`, `classesDirectory`, `executable`,
`compileSourceRoots`, `templateDirectory`, `configFile`, `reportOutputDirectory`.

**Type heuristic:** Java type `File` → always `envSpecific=true`.
`String` with parameter name ending in `Directory`, `Dir`, `File`, `Path`, `Location`,
`Url`, `Executable` → very likely `envSpecific=true`.

---

#### Trigger 2 — Indirect environment lookup

> The value is an abstract selector (version spec, server ID, credential key, profile name)
> that is resolved against a machine-local registry such as `toolchains.xml`, `settings.xml`,
> or the set of locally installed JDKs/SDKs.

Two developers may configure the same server ID in `settings.xml` but point it to different
hosts, or have the same JDK version requirement satisfied by installations at different paths.

**Examples:** `jdkToolchain` (matched against `toolchains.xml`), `serverId` (looked up in
`settings.xml`), `repositoryId`, `settingsKey` (sql-maven-plugin), `keystore` (jarsigner,
when specified as an alias resolved from a machine keystore).

---

#### Trigger 3 — Free-form passthrough

> The type is unstructured (`String`, `List<String>`, `String[]`, `Map<String, String>`)
> **and** the value is passed through to an underlying external tool without Maven interpreting
> it, **and** paths, environment-variable references, or tool-specific flags that embed paths
> are a documented or commonly observed usage.

Even if a specific _value_ in the project POM doesn't contain a path today, the parameter
type and documented usage mean it _can_ — so it must be treated as environment-specific.

**Examples:** `compilerArgs`, `compilerArgument`, `testCompilerArgument`, `jvmArgs` (when
passed to the compiled program, not just to size the JVM), `args` (scala-maven-plugin),
`script` (maven-scripting-plugin), `argLine` (surefire), `dockerEnv`.

---

### `envSpecific=false` — all three conditions must hold

1. The value has a **fixed, constrained format** — boolean, int, charset name, enum keyword,
   Maven GAV coordinate, version string.
2. Maven (or the plugin) **fully interprets the value itself** — it is not passed through to
   an external tool.
3. The **same value produces identical behavior** on any standard Maven installation with the
   same project sources.

**Examples:** `source` (javac source level, a version string), `encoding` (charset name
interpreted by Java), `proc` (enum: `none`, `only`, `full`), `debug` (boolean),
`enablePreview` (boolean), `parameters` (boolean), `annotationProcessorPaths` (list of GAV
coordinates fully interpreted by Maven's dependency resolution), `effort` (SpotBugs enum),
`threshold` (SpotBugs enum), `format` (report output format enum).

---

### Quick reference table

| Java type | Default `envSpecific` | Notes |
|-----------|-----------------------|-------|
| `File` | **true** | Always a file system path |
| `String` (named `*Directory`, `*Dir`, `*File`, `*Path`, `*Location`, `*Url`, `*Executable`) | **true** | Path-carrying by name convention |
| `String` (free-form passthrough to external tool) | **true** | Trigger 3 |
| `String` (abstract selector: server ID, toolchain version) | **true** | Trigger 2 |
| `String` (charset name, enum keyword, version string) | **false** | Fixed constrained format |
| `boolean` | **false** | Binary flag, machine-independent |
| `int` | **false** | Numeric, machine-independent |
| `List<String>` (passed to external tool) | **true** | Trigger 3 |
| `List<Dependency>` (GAV coordinates) | **false** | Fully interpreted by Maven |
| `Map<String, String>` (passed to external tool) | **true** | Trigger 3 |
| `Map<String, String>` (Maven-interpreted config) | **false** | Depends — apply triggers above |

---

## 3. `skipProperty`: true vs false

### Rule

Mark `<skipProperty>true</skipProperty>` **only** for the single parameter of a goal whose
sole purpose is to **completely disable that goal's execution**.

When the cache extension sees that this parameter is set to its skip value, it treats the
goal as a cache hit immediately, allowing downstream modules to restore their cached outputs
without re-running the skipped goal.

### Criteria for `skipProperty=true`

All of the following must hold:

1. The parameter is a **boolean** (or string accepted as `"true"`/`"false"`).
2. When set to `true` (or its documented truthy value), the goal **does nothing** — no
   compilation, no test execution, no artifact generation, no report.
3. The parameter is the **primary** skip mechanism for that goal. A goal has **at most one**
   skip property.

### Common skip properties

| Plugin | Goal | Skip property |
|--------|------|---------------|
| maven-compiler-plugin | `compile` | `skipMain` |
| maven-compiler-plugin | `testCompile` | `skip` |
| maven-surefire-plugin | `test` | `skipTests` / `maven.test.skip` |
| maven-failsafe-plugin | `integration-test` | `skipITs` / `maven.test.skip` |
| maven-deploy-plugin | `deploy` | `maven.deploy.skip` |
| maven-install-plugin | `install` | `maven.install.skip` |
| maven-jar-plugin | `jar` | `maven.jar.skip` |
| maven-source-plugin | `jar-no-fork` | `maven.source.skip` |
| maven-javadoc-plugin | `jar` | `maven.javadoc.skip` |
| maven-gpg-plugin | `sign` | `gpg.skip` |

### What is NOT a skip property

- `failOnError=false` — the goal still runs; it just doesn't fail the build.
- `skipTests=false` — even though it looks like a skip property name, if it is the default
  value the goal runs normally; only record it as `skipProperty=true` if setting it to `true`
  skips execution.
- `generateClient=false` — this changes which artifacts are produced, not whether the goal
  runs; it is `functional`.
- Multiple skip aliases on the same goal — choose the primary/canonical one; mark the others
  as `functional` or `behavioral` as appropriate for their actual effect.

---

## 4. `defaultValue`

Include `<defaultValue>` only when the plugin documentation states an explicit default.
Omit the element entirely (do not write `<defaultValue></defaultValue>`) when:

- the parameter has no documented default, or
- the default is `null` / unset.

This prevents incorrect cache key comparisons when a user's effective value differs from an
empty string.

---

## 5. `versionRange`

Express using Maven version range notation:

| Pattern | Meaning |
|---------|---------|
| `[2.0,)` | Present since version 2.0, no known removal |
| `[3.5,4.0)` | Present from 3.5, removed (or deprecated away) before 4.0 |
| `[3.6.2,3.6.2]` | Present in exactly one version (unusual; prefer open range) |

Rules:
- Use the **"since" version** from plugin documentation as the range start when available.
  Default to `[1.0,)` when the parameter has been present since the plugin's first release
  and no "since" information is documented.
- Leave the end open (`[x.y,)`) unless the parameter is known to have been removed or
  definitively deprecated and replaced. Do not guess at end bounds.
- If a parameter was renamed, create **separate entries** for the old and new names with
  non-overlapping ranges.

---

## 6. Worked classification examples

### maven-compiler-plugin — `compile` goal

| Parameter | `category` | `envSpecific` | Rationale |
|-----------|------------|---------------|-----------|
| `source` | functional | false | Version string interpreted by Maven; same value = same bytecode |
| `encoding` | functional | false | Charset name; Maven interprets it; fixed format |
| `outputDirectory` | functional | true | `File` type; absolute path differs per machine |
| `compilerArgs` | functional | true | `List<String>` passed to javac; paths commonly embedded |
| `annotationProcessorPaths` | functional | false | List of GAV coords; fully resolved by Maven |
| `jdkToolchain` | functional | true | Resolved against machine-local `toolchains.xml` |
| `fork` | behavioral | false | Boolean; same bytecode produced whether forked or not |
| `meminitial` | behavioral | false | JVM heap size for the fork; no effect on output |
| `verbose` | behavioral | false | Console output only; no effect on `.class` files |
| `skipMain` | behavioral | false | **skipProperty=true**; goal does nothing when set |

### maven-surefire-plugin — `test` goal

| Parameter | `category` | `envSpecific` | Rationale |
|-----------|------------|---------------|-----------|
| `includes` | functional | false | Ant patterns; Maven interprets them; fixed format |
| `excludes` | functional | false | Same as above |
| `argLine` | functional | true | Passed to test JVM; paths and env refs commonly embedded |
| `systemPropertyVariables` | functional | true | Passed to test JVM; may embed paths |
| `reuseForks` | behavioral | false | Controls process model; does not affect test results |
| `forkCount` | behavioral | false | Parallelism; does not affect test results |
| `testFailureIgnore` | behavioral | false | Controls build failure; tests still run |
| `skipTests` | behavioral | false | **skipProperty=true** |

### maven-checkstyle-plugin — `check` goal

| Parameter | `category` | `envSpecific` | Rationale |
|-----------|------------|---------------|-----------|
| `configLocation` | functional | true | Path to rules file; may be absolute |
| `suppressionsLocation` | functional | true | Path to suppressions file |
| `violationSeverity` | functional | false | Enum keyword |
| `maxAllowedViolations` | functional | false | Integer threshold; changes what passes |
| `failsOnError` | behavioral | false | Controls build exit; not what is checked |
| `logViolationsToConsole` | behavioral | false | Console output only |
| `skip` | behavioral | false | **skipProperty=true** |

---

## 7. Applying the rules — a checklist for reviewers

For each parameter in a catalog entry, verify:

- [ ] `category` is either `functional` or `behavioral` (no other values)
- [ ] The **decision question** was applied: would a value difference change build output?
- [ ] `envSpecific` is `true` for all `File`-typed parameters
- [ ] `envSpecific` is `true` for all path-carrying `String` parameters
- [ ] `envSpecific` is `true` for all free-form passthrough parameters (`List<String>`, etc.)
- [ ] `envSpecific` is `false` for all boolean and integer parameters
- [ ] `envSpecific` is `false` for all fixed-format strings (charset, enum, version, GAV)
- [ ] At most one `<skipProperty>true</skipProperty>` per goal
- [ ] The skip property parameter is `category=behavioral`
- [ ] `<defaultValue>` is omitted when the plugin has no documented default
- [ ] `versionRange` starts at the documented "since" version, end is open unless removal is known

---

## 8. Reference

| Item | Location |
|------|----------|
| XML schema (MDO) | `src/main/mdo/plugin-catalog.mdo` |
| Catalog XML files | `src/main/resources/META-INF/maven-build-cache-extension/plugins/` |
| Index of loaded catalogs | `src/main/resources/META-INF/maven-build-cache-extension/plugins/index.txt` |
| Registry implementation | `src/main/java/org/apache/maven/buildcache/PluginCatalogRegistry.java` |
| Reconciliation logic | `src/main/java/org/apache/maven/buildcache/xml/CacheConfigImpl.java` |
| Cache key computation | `src/main/java/org/apache/maven/buildcache/BuildCacheMojosExecutionStrategy.java` |
| Adding a new plugin | `.ai/add-plugin-to-inventory.prompt.md` |
| Supported plugins list | `src/site/markdown/supported-plugins.md` |
