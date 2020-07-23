## JSPMethodCallsCheck

Use type `LiferayPortletResponse` to call `getNamespace()`.

#### Example

Incorrect:

`renderResponse.getNamespace()` or `portletResponse.getNamespace()`

Correct:

`liferayPortletResponse.getNamespace()`