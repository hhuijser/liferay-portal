# Performance Checks

Check | File Extensions | Description
----- | --------------- | -----------
[ArrayCheck](checks/array_check.markdown#arraycheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections. |
ConcatCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for correct use of `StringBundler.concat`. |
[ExceptionCheck](checks/exception_check.markdown#exceptioncheck) | .java | Finds private methods that throw unnecessary exception. |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used. |
[GradleDependenciesCheck](checks/gradle_dependencies_check.markdown#gradledependenciescheck) | .gradle | Checks that `petra` modules are not depending on other modules. |
[JSPDefineObjectsCheck](checks/jsp_define_objects_check.markdown#jspdefineobjectscheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks for unnesecarry duplication of code that already exists in `defineObjects`. |
[JSPStringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds cases of inefficient String operations. |
JSPUnusedJSPF | .jsp, .jspf, .tag, .tpl or .vm | Finds `.jspf` files that are not used. |
JSPUnusedTermsCheck | .jsp, .jspf, .tag, .tpl or .vm | Finds taglibs, variables and imports that are unused. |
JavaDataAccessConnectionCheck | .java | Finds calls to `DataAccess.getConnection` (use `DataAccess.getUpgradeOptimizedConnection` instead). |
JavaHibernateSQLCheck | .java | Finds calls to `com.liferay.portal.kernel.dao.orm.Session.createSQLQuery` (use `Session.createSynchronizedSQLQuery` instead). |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | .java | Checks that we do not concatenate more than 3 String objects. |
[JavaServiceTrackerFactoryCheck](checks/java_service_tracker_factory_check.markdown#javaservicetrackerfactorycheck) | .java | Checks that there are no calls to deprecatred method `ServiceTrackerFactory.open(java.lang.Class)`. |
JavaSessionCheck | .java | Finds unnecessary calls to `Session.flush()` (calls that are followed by `Session.clear()`). |
[JavaStringBundlerConcatCheck](checks/java_string_bundler_concat_check.markdown#javastringbundlerconcatcheck) | .java | Finds calls to `StringBundler.concat` with less than 3 parameters. |
JavaStringBundlerInitialCapacityCheck | .java | Checks the initial capacity of new instances of `StringBundler`. |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no unnecessary map iterations. |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | .java | Checks if performance can be improved by using different String operation methods. |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `Validator.equals(Object, Object)`. |