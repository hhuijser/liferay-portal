# Checks for .gradle

Check | Category | Description
----- | -------- | -----------
GradleBlockOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts logic in gradle build files. |
GradleBodyCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consisteny in the body of gradle build files. |
[GradleDependenciesCheck](checks/gradle_dependencies_check.markdown#gradledependenciescheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks that `petra` modules are not depending on other modules. |
[GradleDependencyArtifactsCheck](checks/gradle_dependency_artifacts_check.markdown#gradledependencyartifactscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that value `default` is not used for attribute `version`. |
GradleDependencyConfigurationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
GradleDependencyVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
GradleExportedPackageDependenciesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
GradleImportsCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts and groups imports in `.gradle` files. |
GradleIndentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
GradleJavaVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
GradlePropertiesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
GradleProvidedDependenciesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
[GradleRequiredDependenciesCheck](checks/gradle_required_dependencies_check.markdown#gradlerequireddependenciescheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
GradleStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consisteny in code style. |
[GradleTaskCreationCheck](checks/gradle_task_creation_check.markdown#gradletaskcreationcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that a task is declared on a separate line before the closure. |
GradleTestDependencyVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | |
WhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary whitespace. |