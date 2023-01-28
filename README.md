# Java json logger application

- Enhancing java logging as json

```kotlin
val error = IllegalArgumentException("test error log message")
JsonLog(logger())
    .add("test-key", "test-value")
    .error(error)
    .message("this is a test log message created in {} of lib version {}", LocalDate.of(2022, 8, 12), "1.0.0")
    .run(Level.ERROR, true)
```

```json
{
  "message": "this is a test log message created in 2022-08-12 of lib version 1.0.0",
  "properties": {
    "test-key": "test-value"
  },
  "errorMessage": "test error log message"
}
```
