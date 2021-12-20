# SourceFormatter

## Main Classes in SourceFormatter:

### 1. SourceProcessor

- For every file extension we have a SourceProcessor (JavaSourceProcessor, XMLSourceProcessor etc).
- For every instance of SourceProcessor → `sourceProcessor.format` (different thread for each instance)

These global variables hold information that applies to SourceProcessor.

During setting up SourceProcessors (SourceFormatter.init), the following variables are populated in each instance of SourceProcessor:

```java
Map<String, Properties> _propertiesMap;
```
> We find all relevant `source-formatter.properties` and store in a map, where the key is the fileLocation of the properties file

```java
SourceFormatterArgs _sourceFormatterArgs;
```
> Arguments that are passed when executing SF are set in `_sourceFormatterArgs`

![Pic1](/Pic1.png)

```java
SourceFormatterExcludes _sourceFormatterExcludes;
```
> SourceFormatter will exclude these default directories combined with additional locations that can be specified in `source-formatter.properties#source.formatter.excludes`

```java
SourceFormatterSuppressions _sourceFormatterSuppressions;
```
> `_sourceFormatterSuppressions` is retrieved from a collection of `source-formatter-suppressions.xml` files

![Pic2](/Pic2.png)

### 2. SourceCheck

For every sourceProcessor, we grab the list of sourceChecks from `SourceProcessor._sourceFormatterConfiguration` and perform this check on every file associated with the sourceProcessor.

These global variables hold information that applies to SourceCheck.

When setting up SourceChecks (BaseSourceProcessor._getSourceChecks), the following variable is populated in each instance of SourceCheck:

```java
JSONObject _attributesJSONObject;
```
> This JSONObject stores all attributes from the configuration file (`sourcechecks.xml`) and additional attributes that are set in `source-formatter.properties`:

![Pic3](/Pic3.png)

## Different types of SourceCheck

We have two different types of SourceCheck

1. Check that implements `FileCheck` (by extending `BaseFileCheck`)
    - When a check extends `BaseFileCheck`, the following method needs to be implemented:

        ```java
        protected String doProcess(
                String fileName, String absolutePath, String content)
            throws Exception;
        ```
The method `doProcess` will take the fileName, absolutePath and content of the file that is being formatted. When the logic in the `doProcess` method makes changes to the content and returns that modified content, these changes will be applied to the file.

If we want to warn instead of changing the content of the file, we can call `BaseSourceCheck.addMessage`

Example of a [FileCheck](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/checks/BNDRangeCheck.java)

2. Check that implements `JavaTermCheck` (by extending `BaseJavaTermCheck`)
    - When a check extends `BaseJavaTermCheck`, the following methods need to be implemented:

        ```java
        protected String doProcess(
                String fileName, String absolutePath, JavaTerm javaTerm,
                String fileContent)
            throws Exception;

        protected String[] getCheckableJavaTermNames();
        ```

Only checks that are associated with `JavaSourceProcessor` can extend `BaseJavaTermCheck`.

In order to perform a JavaTermCheck, we first parse the java file:

```java
JavaClass javaClass = JavaClassParser.parseJavaClass(
    fileName, sourceChecksResult.getContent());
```

`javaClass.getChildJavaTerm` returns a list of JavaTerm Objects.
The following classes implement JavaTerm:
  - JavaClass
  - JavaConstructor
  - JavaMethod
  - JavaStaticBlock
  - JavaVariable

The check will only be performed on the JavaTerms that are specified in the method `getCheckableJavaTermNames`
In method `getCheckableJavaTermNames` an array of JavaTerms is specified.

Example of a [JavaTermCheck](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/checks/JavaConstructorParametersCheck.java)