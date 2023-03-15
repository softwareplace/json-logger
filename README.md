# Java json logger application

- Enhancing java logging as json

```kotlin
val error = IllegalArgumentException("test error log message")
JsonLog(kLogger)
    .add("test-key", "test-value")
    .error(error)
    .level(Level.INFO)
    .printStackTracker(true)
    .message("this is a test log message created in {} of lib version {}", LocalDate.of(2022, 8, 12), "1.0.0")
    .run()
```

```java
new JsonLog(logger)
		.add("test-key","test-value")
		.error(error)
		.level(Level.INFO)
		.printStackTracker(true)
		.message("this is a test log message created in {} of lib version {}",LocalDate.of(2022,8,12),"1.0.0")
		.run()
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
