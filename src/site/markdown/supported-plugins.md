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

## Supported Plugins

This page lists all Maven plugins surveyed for the build-cache extension plugin catalog. Plugins
marked **[catalog]** have a bundled parameter catalog that enables automatic cache reconciliation
without any `<reconcile>` configuration. Plugins without a catalog require explicit
`<reconcile>` configuration or a permissive `onUnknownPlugin` setting.

The authoritative list of bundled catalog files is
[
`src/main/resources/META-INF/maven-build-cache-extension/plugins/index.txt`](https://github.com/apache/maven-build-cache-extension/tree/master/src/main/resources/META-INF/maven-build-cache-extension/plugins/index.txt).

---

## Apache Maven Core Plugins

### maven-clean-plugin **[catalog]**

`org.apache.maven.plugins:maven-clean-plugin`

| Goal    | Description                                                              | Version Range |
|---------|--------------------------------------------------------------------------|---------------|
| `clean` | Remove files generated at build-time from the project's output directory | `[2.0,)`      |
| `help`  | Display help information on available goals and parameters               | `[2.5,)`      |

### maven-compiler-plugin **[catalog]**

`org.apache.maven.plugins:maven-compiler-plugin`

| Goal          | Description                                                | Version Range |
|---------------|------------------------------------------------------------|---------------|
| `compile`     | Compile main application sources                           | `[2.0,)`      |
| `testCompile` | Compile test sources                                       | `[2.0,)`      |
| `help`        | Display help information on available goals and parameters | `[2.5,)`      |

### maven-deploy-plugin **[catalog]**

`org.apache.maven.plugins:maven-deploy-plugin`

| Goal          | Description                                                                  | Version Range |
|---------------|------------------------------------------------------------------------------|---------------|
| `deploy`      | Deploy project artifacts to the remote repository                            | `[2.0,)`      |
| `deploy-file` | Deploy a pre-built artifact to a remote repository with explicit coordinates | `[2.0,)`      |
| `help`        | Display help information on available goals and parameters                   | `[2.5,)`      |

### maven-failsafe-plugin **[catalog]**

`org.apache.maven.plugins:maven-failsafe-plugin`

| Goal               | Description                                                           | Version Range |
|--------------------|-----------------------------------------------------------------------|---------------|
| `integration-test` | Run integration tests using the Failsafe framework                    | `[2.5,)`      |
| `verify`           | Verify that integration tests passed by checking the failsafe summary | `[2.5,)`      |
| `help`             | Display help information on available goals and parameters            | `[2.5,)`      |

### maven-install-plugin **[catalog]**

`org.apache.maven.plugins:maven-install-plugin`

| Goal           | Description                                                                      | Version Range |
|----------------|----------------------------------------------------------------------------------|---------------|
| `install`      | Install project artifacts into the local Maven repository                        | `[2.0,)`      |
| `install-file` | Install a pre-built artifact into the local repository with explicit coordinates | `[2.0,)`      |
| `help`         | Display help information on available goals and parameters                       | `[2.5,)`      |

### maven-resources-plugin **[catalog]**

`org.apache.maven.plugins:maven-resources-plugin`

| Goal             | Description                                                                 | Version Range |
|------------------|-----------------------------------------------------------------------------|---------------|
| `resources`      | Copy main source resources to the main output directory, applying filtering | `[2.0,)`      |
| `testResources`  | Copy test resources to the test output directory, applying filtering        | `[2.0,)`      |
| `copy-resources` | Copy resources to an arbitrary directory outside the standard lifecycle     | `[2.3,)`      |
| `help`           | Display help information on available goals and parameters                  | `[2.5,)`      |

### maven-site-plugin **[catalog]**

`org.apache.maven.plugins:maven-site-plugin`

| Goal                | Description                                                | Version Range |
|---------------------|------------------------------------------------------------|---------------|
| `site`              | Generate the project's site documentation                  | `[2.0,)`      |
| `deploy`            | Deploy the generated site to a web server                  | `[2.0,)`      |
| `run`               | Start the site in a local web server for review            | `[2.0,)`      |
| `stage`             | Stage the generated site to a local staging directory      | `[2.0,)`      |
| `stage-deploy`      | Stage and deploy the generated site to a staging server    | `[2.0,)`      |
| `attach-descriptor` | Attach the site descriptor to the project for deployment   | `[2.0,)`      |
| `jar`               | Create a JAR of the site output                            | `[3.3,)`      |
| `help`              | Display help information on available goals and parameters | `[2.5,)`      |

### maven-surefire-plugin **[catalog]**

`org.apache.maven.plugins:maven-surefire-plugin`

| Goal   | Description                                                | Version Range |
|--------|------------------------------------------------------------|---------------|
| `test` | Run unit tests using the Surefire framework                | `[2.0,)`      |
| `help` | Display help information on available goals and parameters | `[2.1,)`      |

### maven-verifier-plugin **[catalog]**

`org.apache.maven.plugins:maven-verifier-plugin`

| Goal     | Description                                                                | Version Range |
|----------|----------------------------------------------------------------------------|---------------|
| `verify` | Verify file existence/absence and regular expressions against file content | `[1.0,)`      |
| `help`   | Display help information on available goals and parameters                 | `[1.0,)`      |

---

## Apache Maven Packaging Plugins

### maven-ear-plugin **[catalog]**

`org.apache.maven.plugins:maven-ear-plugin`

| Goal                       | Description                                                       | Version Range |
|----------------------------|-------------------------------------------------------------------|---------------|
| `ear`                      | Generate an EAR archive from the current project                  | `[2.0,)`      |
| `generate-application-xml` | Generate the deployment descriptor files (`application.xml` etc.) | `[2.0,)`      |
| `help`                     | Display help information on available goals and parameters        | `[2.5,)`      |

### maven-ejb-plugin **[catalog]**

`org.apache.maven.plugins:maven-ejb-plugin`

| Goal   | Description                                                | Version Range |
|--------|------------------------------------------------------------|---------------|
| `ejb`  | Generate EJB jar and optional client jar                   | `[2.0,)`      |
| `help` | Display help information on available goals and parameters | `[2.5,)`      |

### maven-jar-plugin **[catalog]**

`org.apache.maven.plugins:maven-jar-plugin`

| Goal       | Description                                                       | Version Range |
|------------|-------------------------------------------------------------------|---------------|
| `jar`      | Build a JAR from the project's main compiled classes              | `[2.0,)`      |
| `test-jar` | Build a JAR of the project's test classes for use as a dependency | `[2.0,)`      |
| `help`     | Display help information on available goals and parameters        | `[2.0,)`      |

### maven-rar-plugin **[catalog]**

`org.apache.maven.plugins:maven-rar-plugin`

| Goal   | Description                                                        | Version Range |
|--------|--------------------------------------------------------------------|---------------|
| `rar`  | Generate a RAR (Resource Adapter Archive) from the current project | `[2.0,)`      |
| `help` | Display help information on available goals and parameters         | `[2.5,)`      |

### maven-war-plugin **[catalog]**

`org.apache.maven.plugins:maven-war-plugin`

| Goal       | Description                                                          | Version Range |
|------------|----------------------------------------------------------------------|---------------|
| `war`      | Build a WAR from the project's web application sources               | `[2.0,)`      |
| `exploded` | Assemble the web application in exploded (directory) form            | `[2.0,)`      |
| `inplace`  | Assemble the web application in the web application source directory | `[2.0,)`      |
| `manifest` | Generate the web application's manifest                              | `[2.0,)`      |
| `help`     | Display help information on available goals and parameters           | `[2.5,)`      |

### maven-acr-plugin **[catalog]**

`org.apache.maven.plugins:maven-acr-plugin`

| Goal   | Description                                                | Version Range |
|--------|------------------------------------------------------------|---------------|
| `acr`  | Build an Application Client module archive                 | `[1.0,)`      |
| `help` | Display help information on available goals and parameters | `[1.0,)`      |

### maven-shade-plugin **[catalog]**

`org.apache.maven.plugins:maven-shade-plugin`

| Goal    | Description                                                      | Version Range |
|---------|------------------------------------------------------------------|---------------|
| `shade` | Create an uber-JAR by merging dependencies into a single archive | `[1.0,)`      |
| `help`  | Display help information on available goals and parameters       | `[1.0,)`      |

### maven-source-plugin **[catalog]**

`org.apache.maven.plugins:maven-source-plugin`

| Goal               | Description                                                    | Version Range |
|--------------------|----------------------------------------------------------------|---------------|
| `jar`              | Bundle the main project sources into a JAR (forks compile)     | `[2.0,)`      |
| `jar-no-fork`      | Bundle the main project sources into a JAR (no lifecycle fork) | `[2.0.3,)`    |
| `test-jar`         | Bundle the test sources into a JAR (forks test-compile)        | `[2.0.3,)`    |
| `test-jar-no-fork` | Bundle the test sources into a JAR (no lifecycle fork)         | `[2.0.3,)`    |
| `aggregate`        | Aggregate main sources of a multi-module project into one JAR  | `[2.0,)`      |
| `help`             | Display help information on available goals and parameters     | `[2.5,)`      |

### maven-jlink-plugin **[catalog]**

`org.apache.maven.plugins:maven-jlink-plugin`

| Goal    | Description                                                | Version Range |
|---------|------------------------------------------------------------|---------------|
| `jlink` | Create a Java runtime image using `jlink`                  | `[3.0.0,)`    |
| `help`  | Display help information on available goals and parameters | `[3.0.0,)`    |

### maven-jmod-plugin **[catalog]**

`org.apache.maven.plugins:maven-jmod-plugin`

| Goal   | Description                                                | Version Range |
|--------|------------------------------------------------------------|---------------|
| `jmod` | Create a JMOD file using `jmod`                            | `[3.0.0,)`    |
| `help` | Display help information on available goals and parameters | `[3.0.0,)`    |

---

## Apache Maven Reporting Plugins

### maven-changelog-plugin **[catalog]**

`org.apache.maven.plugins:maven-changelog-plugin`

| Goal            | Description                                                    | Version Range |
|-----------------|----------------------------------------------------------------|---------------|
| `changelog`     | Generate a report of all changes recorded in the SCM           | `[2.0,)`      |
| `dev-activity`  | Generate a report of developer activity from the SCM changelog | `[2.0,)`      |
| `file-activity` | Generate a report of file activity from the SCM changelog      | `[2.0,)`      |
| `help`          | Display help information on available goals and parameters     | `[2.5,)`      |

### maven-changes-plugin **[catalog]**

`org.apache.maven.plugins:maven-changes-plugin`

| Goal                    | Description                                                | Version Range |
|-------------------------|------------------------------------------------------------|---------------|
| `announcement-generate` | Generate an announcement from the changes file             | `[2.0,)`      |
| `announcement-mail`     | Send an announcement email                                 | `[2.0,)`      |
| `changes-report`        | Generate a report from the changes file                    | `[2.0,)`      |
| `jira-report`           | Generate a report from a JIRA instance                     | `[2.0,)`      |
| `github-report`         | Generate a report from GitHub issues                       | `[2.12,)`     |
| `help`                  | Display help information on available goals and parameters | `[2.5,)`      |

### maven-checkstyle-plugin **[catalog]**

`org.apache.maven.plugins:maven-checkstyle-plugin`

| Goal                   | Description                                                        | Version Range |
|------------------------|--------------------------------------------------------------------|---------------|
| `checkstyle`           | Generate a Checkstyle report                                       | `[2.0,)`      |
| `check`                | Fail the build if Checkstyle violations exceed a threshold         | `[2.0,)`      |
| `checkstyle-aggregate` | Generate an aggregate Checkstyle report for a multi-module project | `[2.5,)`      |
| `help`                 | Display help information on available goals and parameters         | `[2.5,)`      |

### maven-doap-plugin **[catalog]**

`org.apache.maven.plugins:maven-doap-plugin`

| Goal       | Description                                                | Version Range |
|------------|------------------------------------------------------------|---------------|
| `generate` | Generate a Description of a Project (DOAP) RDF file        | `[1.0,)`      |
| `help`     | Display help information on available goals and parameters | `[1.0,)`      |

### maven-javadoc-plugin **[catalog]**

`org.apache.maven.plugins:maven-javadoc-plugin`

| Goal            | Description                                                | Version Range |
|-----------------|------------------------------------------------------------|---------------|
| `javadoc`       | Generate Javadoc API documentation for the main sources    | `[2.0,)`      |
| `test-javadoc`  | Generate Javadoc API documentation for the test sources    | `[2.0,)`      |
| `jar`           | Bundle Javadoc output into a JAR                           | `[2.0,)`      |
| `test-jar`      | Bundle test Javadoc output into a JAR                      | `[2.0,)`      |
| `aggregate`     | Generate Javadoc for a multi-module project                | `[2.0,)`      |
| `aggregate-jar` | Bundle aggregate Javadoc into a JAR                        | `[2.6,)`      |
| `fix`           | Fix Javadoc tags in source files                           | `[2.6,)`      |
| `help`          | Display help information on available goals and parameters | `[2.5,)`      |

### maven-jdeps-plugin **[catalog]**

`org.apache.maven.plugins:maven-jdeps-plugin`

| Goal                | Description                                                | Version Range |
|---------------------|------------------------------------------------------------|---------------|
| `jdkinternals`      | Scan main classes for use of JDK internal APIs via `jdeps` | `[3.0,)`      |
| `test-jdkinternals` | Scan test classes for use of JDK internal APIs via `jdeps` | `[3.0,)`      |
| `help`              | Display help information on available goals and parameters | `[3.0,)`      |

### maven-jxr-plugin **[catalog]**

`org.apache.maven.jxr:maven-jxr-plugin`

| Goal             | Description                                                                  | Version Range |
|------------------|------------------------------------------------------------------------------|---------------|
| `jxr`            | Generate a cross-reference of the project's main sources                     | `[2.0,)`      |
| `test-jxr`       | Generate a cross-reference of the project's test sources                     | `[2.0,)`      |
| `aggregate`      | Generate an aggregate source cross-reference for a multi-module project      | `[2.0,)`      |
| `test-aggregate` | Generate an aggregate test source cross-reference for a multi-module project | `[2.0,)`      |
| `help`           | Display help information on available goals and parameters                   | `[2.5,)`      |

### maven-pmd-plugin **[catalog]**

`org.apache.maven.plugins:maven-pmd-plugin`

| Goal        | Description                                                | Version Range |
|-------------|------------------------------------------------------------|---------------|
| `pmd`       | Generate a PMD static analysis report                      | `[2.0,)`      |
| `cpd`       | Generate a CPD (copy/paste detection) report               | `[2.0,)`      |
| `check`     | Fail the build if PMD violations exceed a threshold        | `[2.0,)`      |
| `cpd-check` | Fail the build if CPD violations exceed a threshold        | `[2.0,)`      |
| `help`      | Display help information on available goals and parameters | `[2.5,)`      |

### maven-plugin-report-plugin **[catalog]**

`org.apache.maven.plugin-tools:maven-plugin-report-plugin`

| Goal     | Description                                                | Version Range |
|----------|------------------------------------------------------------|---------------|
| `report` | Generate a documentation report for a Maven plugin         | `[3.6,)`      |
| `help`   | Display help information on available goals and parameters | `[3.6,)`      |

### maven-project-info-reports-plugin **[catalog]**

`org.apache.maven.plugins:maven-project-info-reports-plugin`

| Goal                     | Description                                                | Version Range |
|--------------------------|------------------------------------------------------------|---------------|
| `index`                  | Generate the project's index page                          | `[2.0,)`      |
| `dependencies`           | Generate the project's dependency report                   | `[2.0,)`      |
| `dependency-convergence` | Generate a dependency convergence report                   | `[2.0,)`      |
| `dependency-management`  | Generate the dependency management report                  | `[2.0,)`      |
| `team`                   | Generate the project team report                           | `[2.0,)`      |
| `licenses`               | Generate the licenses report                               | `[2.0,)`      |
| `scm`                    | Generate the SCM information report                        | `[2.0,)`      |
| `ci-management`          | Generate the CI management report                          | `[2.0,)`      |
| `issue-management`       | Generate the issue management report                       | `[2.0,)`      |
| `mailing-lists`          | Generate the mailing lists report                          | `[2.0,)`      |
| `modules`                | Generate the modules report for a multi-module project     | `[2.0,)`      |
| `plugin-management`      | Generate the plugin management report                      | `[2.0,)`      |
| `plugins`                | Generate the plugins report                                | `[2.0,)`      |
| `summary`                | Generate the project summary report                        | `[2.0,)`      |
| `help`                   | Display help information on available goals and parameters | `[2.5,)`      |

### maven-surefire-report-plugin **[catalog]**

`org.apache.maven.plugins:maven-surefire-report-plugin`

| Goal                   | Description                                                      | Version Range |
|------------------------|------------------------------------------------------------------|---------------|
| `report`               | Generate a test results report from existing Surefire XML output | `[2.0,)`      |
| `report-only`          | Generate a test results report without forking the tests         | `[2.0,)`      |
| `failsafe-report-only` | Generate a Failsafe integration-test report without forking      | `[3.0,)`      |
| `help`                 | Display help information on available goals and parameters       | `[2.5,)`      |

---

## Apache Maven Tools

### maven-antrun-plugin **[catalog]**

`org.apache.maven.plugins:maven-antrun-plugin`

| Goal   | Description                                                | Version Range |
|--------|------------------------------------------------------------|---------------|
| `run`  | Execute an Ant task embedded in the POM                    | `[1.1,)`      |
| `help` | Display help information on available goals and parameters | `[1.7,)`      |

### maven-artifact-plugin **[catalog]**

`org.apache.maven.plugins:maven-artifact-plugin`

| Goal              | Description                                                           | Version Range |
|-------------------|-----------------------------------------------------------------------|---------------|
| `buildinfo`       | Record build environment information for reproducibility verification | `[3.0,)`      |
| `compare`         | Compare a local artifact against a reference build                    | `[3.0,)`      |
| `check-buildplan` | Check that the build plan produces reproducible artifacts             | `[3.2,)`      |
| `help`            | Display help information on available goals and parameters            | `[3.0,)`      |

### maven-archetype-plugin **[catalog]**

`org.apache.maven.archetype:maven-archetype-plugin`

| Goal                   | Description                                                  | Version Range |
|------------------------|--------------------------------------------------------------|---------------|
| `generate`             | Generate a project from a Maven archetype                    | `[2.0,)`      |
| `create-from-project`  | Create an archetype from the current project                 | `[2.0,)`      |
| `crawl`                | Crawl repositories to find archetypes and update the catalog | `[2.0,)`      |
| `update-local-catalog` | Update the local archetype catalog                           | `[2.0,)`      |
| `help`                 | Display help information on available goals and parameters   | `[2.0,)`      |

### maven-assembly-plugin **[catalog]**

`org.apache.maven.plugins:maven-assembly-plugin`

| Goal     | Description                                                           | Version Range |
|----------|-----------------------------------------------------------------------|---------------|
| `single` | Assemble a distribution archive from the project and its dependencies | `[2.2,)`      |
| `help`   | Display help information on available goals and parameters            | `[2.2,)`      |

### maven-dependency-plugin **[catalog]**

`org.apache.maven.plugins:maven-dependency-plugin`

| Goal                     | Description                                                        | Version Range |
|--------------------------|--------------------------------------------------------------------|---------------|
| `copy`                   | Copy specific artifacts to an output directory                     | `[1.0,)`      |
| `copy-dependencies`      | Copy project dependencies to an output directory                   | `[1.0,)`      |
| `unpack`                 | Unpack specific artifacts to an output directory                   | `[1.0,)`      |
| `unpack-dependencies`    | Unpack project dependencies to an output directory                 | `[1.0,)`      |
| `resolve`                | Display resolved dependencies and their versions                   | `[2.0,)`      |
| `resolve-plugins`        | Resolve and display build plugin and their dependencies            | `[2.0,)`      |
| `analyze`                | Analyze declared vs. used dependencies                             | `[2.0,)`      |
| `analyze-only`           | Analyze dependencies without forking the lifecycle                 | `[2.0,)`      |
| `analyze-dep-mgt`        | Analyze dependency management section for inconsistencies          | `[2.0,)`      |
| `tree`                   | Display the dependency tree                                        | `[2.0,)`      |
| `list`                   | List resolved dependencies                                         | `[2.0,)`      |
| `list-classes`           | List classes/resources in a resolved artifact                      | `[3.1,)`      |
| `build-classpath`        | Build the classpath of dependencies and optionally write to a file | `[2.0,)`      |
| `purge-local-repository` | Purge project dependencies from the local repository               | `[2.0,)`      |
| `go-offline`             | Resolve all dependencies needed to build the project offline       | `[2.0,)`      |
| `help`                   | Display help information on available goals and parameters         | `[2.5,)`      |

### maven-enforcer-plugin **[catalog]**

`org.apache.maven.plugins:maven-enforcer-plugin`

| Goal           | Description                                                | Version Range |
|----------------|------------------------------------------------------------|---------------|
| `enforce`      | Execute project constraint rules and fail on violations    | `[1.0,)`      |
| `display-info` | Display environment information that rules can check       | `[1.0,)`      |
| `help`         | Display help information on available goals and parameters | `[1.0,)`      |

### maven-gpg-plugin **[catalog]**

`org.apache.maven.plugins:maven-gpg-plugin`

| Goal                   | Description                                                | Version Range |
|------------------------|------------------------------------------------------------|---------------|
| `sign`                 | Sign project artifacts with GnuPG                          | `[1.0,)`      |
| `sign-and-deploy-file` | Sign and deploy a pre-built artifact to a repository       | `[1.0,)`      |
| `help`                 | Display help information on available goals and parameters | `[1.0,)`      |

### maven-help-plugin **[catalog]**

`org.apache.maven.plugins:maven-help-plugin`

| Goal                 | Description                                                | Version Range |
|----------------------|------------------------------------------------------------|---------------|
| `active-profiles`    | Display the list of active profiles for the current build  | `[2.0,)`      |
| `all-profiles`       | Display all profiles defined in the current project        | `[2.0,)`      |
| `describe`           | Describe the attributes of a plugin or a plugin goal       | `[2.0,)`      |
| `effective-pom`      | Display the effective POM for the current build            | `[2.0,)`      |
| `effective-settings` | Display the calculated settings for the current build      | `[2.0,)`      |
| `system`             | Display a list of platform details for the current build   | `[2.0,)`      |
| `evaluate`           | Evaluate a Maven expression given a project                | `[3.0.0,)`    |
| `help`               | Display help information on available goals and parameters | `[2.5,)`      |

### maven-invoker-plugin **[catalog]**

`org.apache.maven.plugins:maven-invoker-plugin`

| Goal      | Description                                                                 | Version Range |
|-----------|-----------------------------------------------------------------------------|---------------|
| `install` | Install project artifacts into the local repository for integration testing | `[1.0,)`      |
| `run`     | Run a set of integration tests defined in sub-projects                      | `[1.0,)`      |
| `verify`  | Verify the results of integration test runs                                 | `[1.0,)`      |
| `report`  | Generate a report of integration test results                               | `[1.6,)`      |
| `help`    | Display help information on available goals and parameters                  | `[1.0,)`      |

### maven-jarsigner-plugin **[catalog]**

`org.apache.maven.plugins:maven-jarsigner-plugin`

| Goal     | Description                                                | Version Range |
|----------|------------------------------------------------------------|---------------|
| `sign`   | Sign project artifacts using `jarsigner`                   | `[1.0,)`      |
| `verify` | Verify signatures on project artifacts using `jarsigner`   | `[1.0,)`      |
| `help`   | Display help information on available goals and parameters | `[1.0,)`      |

### maven-jdeprscan-plugin **[catalog]**

`org.apache.maven.plugins:maven-jdeprscan-plugin`

| Goal             | Description                                                        | Version Range |
|------------------|--------------------------------------------------------------------|---------------|
| `jdeprscan`      | Scan main classes for use of deprecated JDK APIs using `jdeprscan` | `[3.0,)`      |
| `test-jdeprscan` | Scan test classes for use of deprecated JDK APIs using `jdeprscan` | `[3.0,)`      |
| `help`           | Display help information on available goals and parameters         | `[3.0,)`      |

### maven-plugin-plugin **[catalog]**

`org.apache.maven.plugin-tools:maven-plugin-plugin`

| Goal                        | Description                                                               | Version Range |
|-----------------------------|---------------------------------------------------------------------------|---------------|
| `descriptor`                | Generate the plugin descriptor (`plugin.xml`) from annotated mojo sources | `[2.0,)`      |
| `addPluginArtifactMetadata` | Add artifact metadata for the plugin                                      | `[2.0,)`      |
| `helpmojo`                  | Generate a `help` mojo for the plugin                                     | `[2.0,)`      |
| `report`                    | Generate the plugin documentation report                                  | `[2.0,)`      |
| `help`                      | Display help information on available goals and parameters                | `[2.5,)`      |

### maven-release-plugin **[catalog]**

`org.apache.maven.plugins:maven-release-plugin`

| Goal              | Description                                                   | Version Range |
|-------------------|---------------------------------------------------------------|---------------|
| `prepare`         | Prepare a release: update versions, tag SCM, and verify build | `[2.0,)`      |
| `perform`         | Execute the release from the tagged source in SCM             | `[2.0,)`      |
| `rollback`        | Rollback a failed release preparation                         | `[2.0,)`      |
| `branch`          | Create a branch from the current project with a new version   | `[2.0,)`      |
| `update-versions` | Update the version of the project to a new value              | `[2.2,)`      |
| `stage`           | Perform a release into a staging area                         | `[2.2,)`      |
| `help`            | Display help information on available goals and parameters    | `[2.0,)`      |

### maven-remote-resources-plugin **[catalog]**

`org.apache.maven.plugins:maven-remote-resources-plugin`

| Goal      | Description                                                  | Version Range |
|-----------|--------------------------------------------------------------|---------------|
| `bundle`  | Bundle a set of resources into a JAR for remote use          | `[1.0,)`      |
| `process` | Retrieve and filter remote resource bundles for this project | `[1.0,)`      |
| `help`    | Display help information on available goals and parameters   | `[1.0,)`      |

### maven-scm-plugin **[catalog]**

`org.apache.maven.scm:maven-scm-plugin`

| Goal       | Description                                                     | Version Range |
|------------|-----------------------------------------------------------------|---------------|
| `checkout` | Check out code from SCM                                         | `[1.0,)`      |
| `update`   | Update the working copy with the latest SCM changes             | `[1.0,)`      |
| `checkin`  | Commit changes from the working copy to SCM                     | `[1.0,)`      |
| `add`      | Stage new files for the next SCM commit                         | `[1.0,)`      |
| `status`   | Display SCM status of the working copy                          | `[1.0,)`      |
| `tag`      | Tag the current working copy                                    | `[1.0,)`      |
| `branch`   | Create a branch from the current working copy                   | `[1.0,)`      |
| `diff`     | Display differences between the working copy and the repository | `[1.0,)`      |
| `validate` | Validate the SCM connection details in the POM                  | `[1.0,)`      |
| `help`     | Display help information on available goals and parameters      | `[1.0,)`      |

### maven-scm-publish-plugin **[catalog]**

`org.apache.maven.plugins:maven-scm-publish-plugin`

| Goal          | Description                                                         | Version Range |
|---------------|---------------------------------------------------------------------|---------------|
| `publish-scm` | Publish a directory tree to SCM (e.g. publish site to GitHub Pages) | `[1.0,)`      |
| `help`        | Display help information on available goals and parameters          | `[1.0,)`      |

### maven-scripting-plugin **[catalog]**

`org.apache.maven.plugins:maven-scripting-plugin`

| Goal   | Description                                                    | Version Range |
|--------|----------------------------------------------------------------|---------------|
| `eval` | Evaluate an inline or file-based script using a JSR-223 engine | `[3.0.0,)`    |
| `help` | Display help information on available goals and parameters     | `[3.0.0,)`    |

### maven-stage-plugin **[catalog]**

`org.apache.maven.plugins:maven-stage-plugin`

| Goal   | Description                                                      | Version Range |
|--------|------------------------------------------------------------------|---------------|
| `copy` | Copy artifacts from a staging repository to the final repository | `[1.0,)`      |
| `help` | Display help information on available goals and parameters       | `[1.0,)`      |

### maven-toolchains-plugin **[catalog]**

`org.apache.maven.plugins:maven-toolchains-plugin`

| Goal                | Description                                                             | Version Range |
|---------------------|-------------------------------------------------------------------------|---------------|
| `toolchain`         | Select the toolchain to use for the current build based on requirements | `[1.0,)`      |
| `display-toolchain` | Display the currently active toolchain                                  | `[3.1.0,)`    |
| `help`              | Display help information on available goals and parameters              | `[1.0,)`      |

### maven-wrapper-plugin **[catalog]**

`org.apache.maven.plugins:maven-wrapper-plugin`

| Goal      | Description                                                    | Version Range |
|-----------|----------------------------------------------------------------|---------------|
| `wrapper` | Generate Maven Wrapper scripts and JAR for the current project | `[3.0.0,)`    |
| `help`    | Display help information on available goals and parameters     | `[3.0.0,)`    |

---

## MojoHaus Plugins (Outside The Maven Land)

### animal-sniffer-maven-plugin **[catalog]**

`org.codehaus.mojo:animal-sniffer-maven-plugin`

| Goal    | Description                                                                                | Version Range |
|---------|--------------------------------------------------------------------------------------------|---------------|
| `check` | Check that classes compiled against a newer JDK signature are compatible with an older one | `[1.0,)`      |
| `build` | Build an API signature from current JDK or artifact                                        | `[1.0,)`      |
| `help`  | Display help information on available goals and parameters                                 | `[1.0,)`      |

### build-helper-maven-plugin **[catalog]**

`org.codehaus.mojo:build-helper-maven-plugin`

| Goal                   | Description                                                                | Version Range |
|------------------------|----------------------------------------------------------------------------|---------------|
| `add-source`           | Add extra source directories to the project                                | `[1.0,)`      |
| `add-test-source`      | Add extra test source directories to the project                           | `[1.5,)`      |
| `add-resource`         | Add extra resource directories to the project                              | `[1.7,)`      |
| `add-test-resource`    | Add extra test resource directories to the project                         | `[1.7,)`      |
| `attach-artifact`      | Attach additional artifacts to the project for installation and deployment | `[1.0,)`      |
| `parse-version`        | Parse the project version into major, minor, and incremental components    | `[1.5,)`      |
| `regex-property`       | Set a property value based on a regular expression replacement             | `[1.7,)`      |
| `regex-properties`     | Set multiple properties using regular expression replacements              | `[3.0,)`      |
| `reserve-network-port` | Reserve free network ports and expose them as properties                   | `[1.5,)`      |
| `timestamp-property`   | Set a property to the current date/time with a given format                | `[1.9,)`      |
| `bsh-property`         | Set a property by evaluating a BeanShell expression                        | `[1.5,)`      |
| `help`                 | Display help information on available goals and parameters                 | `[1.0,)`      |

### buildplan-maven-plugin **[catalog]**

`org.codehaus.mojo:buildplan-maven-plugin`

| Goal                | Description                                                | Version Range |
|---------------------|------------------------------------------------------------|---------------|
| `list`              | List all mojos bound to the build lifecycle                | `[1.0,)`      |
| `list-phases`       | List build phases and the mojos bound to each              | `[1.0,)`      |
| `list-plugin-infos` | List plugin information for all mojos in the build plan    | `[1.0,)`      |
| `help`              | Display help information on available goals and parameters | `[1.0,)`      |

### castor-maven-plugin **[catalog]**

`org.codehaus.mojo:castor-maven-plugin`

| Goal       | Description                                                | Version Range |
|------------|------------------------------------------------------------|---------------|
| `generate` | Generate Java source files from XML schema using Castor    | `[1.0,)`      |
| `mapping`  | Generate Castor mapping files from existing Java classes   | `[1.0,)`      |
| `help`     | Display help information on available goals and parameters | `[1.0,)`      |

### clirr-maven-plugin **[catalog]**

`org.codehaus.mojo:clirr-maven-plugin`

| Goal            | Description                                                                          | Version Range |
|-----------------|--------------------------------------------------------------------------------------|---------------|
| `check`         | Check binary and source compatibility of the current code against a previous version | `[2.0,)`      |
| `check-no-fork` | Check compatibility without forking the lifecycle                                    | `[2.0,)`      |
| `clirr`         | Generate a Clirr compatibility report                                                | `[2.0,)`      |
| `help`          | Display help information on available goals and parameters                           | `[2.0,)`      |

### exec-maven-plugin **[catalog]**

`org.codehaus.mojo:exec-maven-plugin`

| Goal   | Description                                                | Version Range |
|--------|------------------------------------------------------------|---------------|
| `exec` | Execute an external program during the build               | `[1.0,)`      |
| `java` | Execute a Java class in the current VM (or a forked VM)    | `[1.0,)`      |
| `help` | Display help information on available goals and parameters | `[1.0,)`      |

### flatten-maven-plugin **[catalog]**

`org.codehaus.mojo:flatten-maven-plugin`

| Goal      | Description                                                               | Version Range |
|-----------|---------------------------------------------------------------------------|---------------|
| `flatten` | Flatten the POM, resolving properties and removing build-only information | `[1.0,)`      |
| `clean`   | Remove the flattened POM file                                             | `[1.0,)`      |
| `help`    | Display help information on available goals and parameters                | `[1.0,)`      |

### javacc-maven-plugin **[catalog]**

`org.codehaus.mojo:javacc-maven-plugin`

| Goal         | Description                                                   | Version Range |
|--------------|---------------------------------------------------------------|---------------|
| `javacc`     | Generate Java parser sources from a JavaCC grammar file       | `[2.0,)`      |
| `jjtree`     | Pre-process a JJTree grammar file and generate JavaCC grammar | `[2.0,)`      |
| `jtb-javacc` | Generate Java parser from a JTB-based JavaCC grammar          | `[2.0,)`      |
| `jjdoc`      | Generate documentation from a JavaCC grammar using JJDoc      | `[2.0,)`      |
| `help`       | Display help information on available goals and parameters    | `[2.0,)`      |

### jdepend-maven-plugin **[catalog]**

`org.codehaus.mojo:jdepend-maven-plugin`

| Goal       | Description                                                | Version Range |
|------------|------------------------------------------------------------|---------------|
| `generate` | Generate a JDepend report in XML format                    | `[2.0,)`      |
| `jdepend`  | Produce the JDepend report as a site page                  | `[2.0,)`      |
| `help`     | Display help information on available goals and parameters | `[2.0,)`      |

### nar-maven-plugin **[catalog]**

`org.apache.maven.plugins:nar-maven-plugin`

| Goal           | Description                                                | Version Range |
|----------------|------------------------------------------------------------|---------------|
| `nar-compile`  | Compile native (C/C++/Fortran) sources                     | `[3.0,)`      |
| `nar-test`     | Compile native test sources                                | `[3.0,)`      |
| `nar-package`  | Package native artifacts into a NAR archive                | `[3.0,)`      |
| `nar-download` | Download NAR dependencies                                  | `[3.0,)`      |
| `nar-unpack`   | Unpack NAR dependencies                                    | `[3.0,)`      |
| `nar-install`  | Install NAR artifacts into the local repository            | `[3.0,)`      |
| `nar-validate` | Validate the NAR project structure                         | `[3.0,)`      |
| `help`         | Display help information on available goals and parameters | `[3.0,)`      |

### native-maven-plugin **[catalog]**

`org.codehaus.mojo:native-maven-plugin`

| Goal      | Description                                                    | Version Range |
|-----------|----------------------------------------------------------------|---------------|
| `compile` | Compile native (C/C++) source files                            | `[1.0,)`      |
| `link`    | Link compiled native object files into a library or executable | `[1.0,)`      |
| `help`    | Display help information on available goals and parameters     | `[1.0,)`      |

### sql-maven-plugin **[catalog]**

`org.codehaus.mojo:sql-maven-plugin`

| Goal      | Description                                                                | Version Range |
|-----------|----------------------------------------------------------------------------|---------------|
| `execute` | Execute SQL statements from files or inline definitions against a database | `[1.0,)`      |
| `help`    | Display help information on available goals and parameters                 | `[1.0,)`      |

### taglist-maven-plugin **[catalog]**

`org.codehaus.mojo:taglist-maven-plugin`

| Goal      | Description                                                         | Version Range |
|-----------|---------------------------------------------------------------------|---------------|
| `taglist` | Generate a report of tags (TODO, FIXME, etc.) found in source files | `[2.0,)`      |
| `help`    | Display help information on available goals and parameters          | `[2.0,)`      |

### versions-maven-plugin **[catalog]**

`org.codehaus.mojo:versions-maven-plugin`

| Goal                         | Description                                                              | Version Range |
|------------------------------|--------------------------------------------------------------------------|---------------|
| `set`                        | Set new version numbers for the project and its modules                  | `[1.0,)`      |
| `update-parent`              | Update the parent POM version to the latest available version            | `[1.0,)`      |
| `update-properties`          | Update properties to the latest available dependency version             | `[1.0,)`      |
| `update-child-modules`       | Update the `<parent>` version in child modules to match the parent POM   | `[1.0,)`      |
| `display-dependency-updates` | Display the available updates for project dependencies                   | `[1.0,)`      |
| `display-plugin-updates`     | Display the available updates for build plugins                          | `[1.0,)`      |
| `display-property-updates`   | Display properties that control artifact versions with available updates | `[1.0,)`      |
| `use-latest-releases`        | Update dependencies to their latest release versions                     | `[1.0,)`      |
| `use-latest-snapshots`       | Update dependencies to their latest snapshot versions                    | `[1.0,)`      |
| `use-latest-versions`        | Update dependencies to their latest versions, including snapshots        | `[1.0,)`      |
| `use-next-releases`          | Update dependencies to their next release version                        | `[1.0,)`      |
| `use-next-snapshots`         | Update dependencies to their next snapshot version                       | `[1.0,)`      |
| `use-next-versions`          | Update dependencies to their next available version                      | `[1.0,)`      |
| `commit`                     | Remove backup POMs created during version update operations              | `[1.0,)`      |
| `revert`                     | Restore backup POMs created during version update operations             | `[1.0,)`      |
| `help`                       | Display help information on available goals and parameters               | `[1.0,)`      |

---

## Popular Third-Party Plugins

### spring-boot-maven-plugin **[catalog]**

`org.springframework.boot:spring-boot-maven-plugin`

| Goal          | Description                                                             | Version Range |
|---------------|-------------------------------------------------------------------------|---------------|
| `repackage`   | Repackage an existing JAR/WAR into an executable Spring Boot archive    | `[1.0,)`      |
| `run`         | Run the application in-process using an embedded container              | `[1.0,)`      |
| `start`       | Start the application in a forked process for integration testing       | `[1.3,)`      |
| `stop`        | Stop an application that was started with `start`                       | `[1.3,)`      |
| `build-image` | Package the application into an OCI image using Cloud Native Buildpacks | `[2.3,)`      |
| `build-info`  | Generate a `build-info.properties` file for the Actuator                | `[1.3,)`      |
| `help`        | Display help information on available goals and parameters              | `[1.0,)`      |

### jacoco-maven-plugin **[catalog]**

`org.jacoco:jacoco-maven-plugin`

| Goal                           | Description                                                              | Version Range |
|--------------------------------|--------------------------------------------------------------------------|---------------|
| `prepare-agent`                | Set up the JaCoCo Java agent for unit test coverage collection           | `[0.5,)`      |
| `prepare-agent-integration`    | Set up the JaCoCo Java agent for integration test coverage collection    | `[0.6.4,)`    |
| `report`                       | Generate a code coverage report from the unit test execution data        | `[0.5,)`      |
| `report-integration`           | Generate a code coverage report from the integration test execution data | `[0.6.4,)`    |
| `check`                        | Verify that the code coverage metrics meet the configured rules          | `[0.6.3,)`    |
| `dump`                         | Request a dump from the JaCoCo agent over TCP socket                     | `[0.6.4,)`    |
| `instrument`                   | Perform offline instrumentation of class files                           | `[0.6.2,)`    |
| `restore-instrumented-classes` | Restore original class files after offline instrumentation               | `[0.6.2,)`    |
| `help`                         | Display help information on available goals and parameters               | `[0.5,)`      |

### spotbugs-maven-plugin **[catalog]**

`com.github.spotbugs:spotbugs-maven-plugin`

| Goal       | Description                                                                   | Version Range |
|------------|-------------------------------------------------------------------------------|---------------|
| `spotbugs` | Produce a SpotBugs report without failing the build                           | `[3.0,)`      |
| `check`    | Run SpotBugs and fail the build if violations exceed the configured threshold | `[3.0,)`      |
| `gui`      | Open the SpotBugs GUI to browse the analysis results                          | `[3.0,)`      |
| `help`     | Display help information on available goals and parameters                    | `[3.0,)`      |

### exec-maven-plugin (MojoHaus) **[catalog]**

`org.codehaus.mojo:exec-maven-plugin`

_(Listed above under MojoHaus Plugins)_

### versions-maven-plugin (MojoHaus) **[catalog]**

`org.codehaus.mojo:versions-maven-plugin`

_(Listed above under MojoHaus Plugins)_

### sonar-maven-plugin **[catalog]**

`org.sonarsource.scanner.maven:sonar-maven-plugin`

| Goal    | Description                                                          | Version Range |
|---------|----------------------------------------------------------------------|---------------|
| `sonar` | Run the SonarQube analysis and publish results to a SonarQube server | `[3.0,)`      |
| `help`  | Display help information on available goals and parameters           | `[3.0,)`      |

### git-commit-id-maven-plugin **[catalog]**

`io.github.git-commit-id:git-commit-id-maven-plugin`

| Goal               | Description                                                         | Version Range |
|--------------------|---------------------------------------------------------------------|---------------|
| `revision`         | Generate a properties file with Git commit metadata                 | `[2.1,)`      |
| `validateRevision` | Validate the generated Git commit metadata against configured rules | `[2.2,)`      |
| `help`             | Display help information on available goals and parameters          | `[2.1,)`      |

### license-maven-plugin **[catalog]**

`com.mycila:license-maven-plugin`

| Goal     | Description                                                | Version Range |
|----------|------------------------------------------------------------|---------------|
| `check`  | Check that source files have the required license header   | `[2.0,)`      |
| `format` | Add or update the license header in source files           | `[2.0,)`      |
| `remove` | Remove the license header from source files                | `[2.0,)`      |
| `help`   | Display help information on available goals and parameters | `[2.0,)`      |

### flyway-maven-plugin **[catalog]**

`org.flywaydb:flyway-maven-plugin`

| Goal       | Description                                                                | Version Range |
|------------|----------------------------------------------------------------------------|---------------|
| `migrate`  | Migrates the database to the latest version                                | `[3.0,)`      |
| `clean`    | Drops all database objects in the configured schemas                       | `[3.0,)`      |
| `info`     | Print the status and version history of all migrations                     | `[3.0,)`      |
| `validate` | Validate the applied migrations against the available classpath migrations | `[3.0,)`      |
| `baseline` | Baseline an existing database at the current version                       | `[3.0,)`      |
| `repair`   | Repair the Flyway schema history table                                     | `[3.0,)`      |
| `help`     | Display help information on available goals and parameters                 | `[3.0,)`      |

### liquibase-maven-plugin **[catalog]**

`org.liquibase:liquibase-maven-plugin`

| Goal                | Description                                                | Version Range |
|---------------------|------------------------------------------------------------|---------------|
| `update`            | Apply pending database changesets                          | `[3.0,)`      |
| `updateSQL`         | Generate SQL for pending changesets without applying them  | `[3.0,)`      |
| `rollback`          | Roll back the database to a previous state                 | `[3.0,)`      |
| `rollbackSQL`       | Generate rollback SQL without applying it                  | `[3.0,)`      |
| `status`            | Print pending changesets                                   | `[3.0,)`      |
| `validate`          | Validate the changelog                                     | `[3.0,)`      |
| `diff`              | Diff two databases and print the differences               | `[3.0,)`      |
| `generateChangeLog` | Generate a changelog from an existing database             | `[3.0,)`      |
| `help`              | Display help information on available goals and parameters | `[3.0,)`      |

### docker-maven-plugin **[catalog]**

`io.fabric8:docker-maven-plugin`

| Goal     | Description                                                | Version Range |
|----------|------------------------------------------------------------|---------------|
| `build`  | Build Docker images defined in the plugin configuration    | `[0.1,)`      |
| `push`   | Push built images to a Docker registry                     | `[0.1,)`      |
| `start`  | Create and start containers                                | `[0.1,)`      |
| `stop`   | Stop and optionally remove containers                      | `[0.1,)`      |
| `remove` | Remove Docker images                                       | `[0.1,)`      |
| `help`   | Display help information on available goals and parameters | `[0.1,)`      |

### scala-maven-plugin **[catalog]**

`net.alchim31.maven:scala-maven-plugin`

| Goal          | Description                                                | Version Range |
|---------------|------------------------------------------------------------|---------------|
| `compile`     | Compile main Scala and Java sources                        | `[3.0,)`      |
| `testCompile` | Compile test Scala and Java sources                        | `[3.0,)`      |
| `run`         | Run a Scala class                                          | `[3.0,)`      |
| `console`     | Launch the Scala REPL                                      | `[3.0,)`      |
| `doc`         | Generate Scaladoc documentation                            | `[3.0,)`      |
| `help`        | Display help information on available goals and parameters | `[3.0,)`      |

### asciidoctor-maven-plugin **[catalog]**

`org.asciidoctor:asciidoctor-maven-plugin`

| Goal               | Description                                                | Version Range |
|--------------------|------------------------------------------------------------|---------------|
| `process-asciidoc` | Convert AsciiDoc sources to the configured output format   | `[1.5,)`      |
| `http`             | Serve generated output from an embedded HTTP server        | `[2.0,)`      |
| `help`             | Display help information on available goals and parameters | `[1.5,)`      |

### buildnumber-maven-plugin **[catalog]**

`org.codehaus.mojo:buildnumber-maven-plugin`

| Goal               | Description                                                            | Version Range |
|--------------------|------------------------------------------------------------------------|---------------|
| `create`           | Create a unique build number from SCM revision, timestamp, or sequence | `[1.0,)`      |
| `create-timestamp` | Create a timestamp-based build number property                         | `[1.0,)`      |
| `create-metadata`  | Write the build number to a metadata file                              | `[1.4,)`      |
| `help`             | Display help information on available goals and parameters             | `[1.0,)`      |
| `help`             | Display help information on available goals and parameters             | `[1.0,)` |

---

## Additional Popular Plugins

### spotless-maven-plugin **[catalog]**

`com.diffplug.spotless:spotless-maven-plugin`

| Goal      | Description                                                                        | Version Range |
|-----------|------------------------------------------------------------------------------------|---------------|
| `check`   | Verify that all source files match configured formatting rules; fails if they don't | `[1.0,)`      |
| `apply`   | Apply configured formatting rules to source files in-place                          | `[1.0,)`      |
| `help`    | Display help information on available goals and parameters                          | `[1.0,)`      |

### dependency-check-maven **[catalog]**

`org.owasp:dependency-check-maven`

| Goal            | Description                                                              | Version Range |
|-----------------|--------------------------------------------------------------------------|---------------|
| `check`         | Scan project dependencies for known CVE vulnerabilities                  | `[1.0,)`      |
| `aggregate`     | Aggregate vulnerability report across all modules in a multi-module build | `[1.0,)`      |
| `update-only`   | Update the local NVD vulnerability database without running analysis     | `[1.0,)`      |
| `purge`         | Purge the local NVD database                                             | `[1.0,)`      |
| `help`          | Display help information on available goals and parameters               | `[1.0,)`      |

### jib-maven-plugin **[catalog]**

`com.google.cloud.tools:jib-maven-plugin`

| Goal           | Description                                                           | Version Range |
|----------------|-----------------------------------------------------------------------|---------------|
| `build`        | Build and push a container image to a remote registry (no Docker needed) | `[0.1,)`   |
| `dockerBuild`  | Build a container image and load it into the local Docker daemon      | `[0.1,)`      |
| `buildTar`     | Build a container image and export it as a TAR archive                | `[0.9,)`      |
| `help`         | Display help information on available goals and parameters            | `[0.1,)`      |

### lombok-maven-plugin **[catalog]**

`org.projectlombok:lombok-maven-plugin`

| Goal             | Description                                                   | Version Range  |
|------------------|---------------------------------------------------------------|----------------|
| `delombok`       | Expand Lombok annotations to generated Java code in main sources | `[1.0,)`    |
| `testDelombok`   | Expand Lombok annotations to generated Java code in test sources | `[1.0,)`    |
| `help`           | Display help information on available goals and parameters    | `[1.0,)`       |

### findbugs-maven-plugin **[catalog]**

`org.codehaus.mojo:findbugs-maven-plugin`

> **Note:** FindBugs is no longer maintained. Prefer [spotbugs-maven-plugin](#spotbugs-maven-plugin-catalog) for new projects.

| Goal        | Description                                                     | Version Range |
|-------------|-----------------------------------------------------------------|---------------|
| `findbugs`  | Analyze class files for bugs and produce a FindBugs report      | `[1.0,)`      |
| `check`     | Run FindBugs analysis and fail the build if violations are found | `[2.0,)`     |
| `findbugs-no-fork` | Same as findbugs but skips the test-compile phase        | `[2.0,)`      |
| `help`      | Display help information on available goals and parameters      | `[1.0,)`      |

### openapi-generator-maven-plugin **[catalog]**

`org.openapitools:openapi-generator-maven-plugin`

| Goal       | Description                                                              | Version Range |
|------------|--------------------------------------------------------------------------|---------------|
| `generate` | Generate client/server/model code from an OpenAPI specification          | `[3.0,)`      |
| `validate` | Validate an OpenAPI specification for correctness                        | `[3.0,)`      |
| `help`     | Display help information on available goals and parameters               | `[3.0,)`      |

### helm-maven-plugin **[catalog]**

`io.kokuwa.maven:helm-maven-plugin`

| Goal                 | Description                                                          | Version Range |
|----------------------|----------------------------------------------------------------------|---------------|
| `init`               | Download the Helm binary and initialize the Helm environment         | `[1.0,)`      |
| `dependency-build`   | Download and build Helm chart dependencies                           | `[1.0,)`      |
| `lint`               | Validate chart syntax and structure with helm lint                   | `[1.0,)`      |
| `template`           | Render chart templates locally without deploying                     | `[4.0,)`      |
| `package`            | Package a Helm chart into a versioned .tgz archive                   | `[1.0,)`      |
| `upload`             | Upload a packaged chart archive to a Helm chart repository           | `[2.0,)`      |
| `test`               | Run Helm chart tests against a deployed release                      | `[3.0,)`      |
| `help`               | Display help information on available goals and parameters           | `[1.0,)`      |
