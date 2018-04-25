# BlockApps Vert.x REST API

This is a simple wrapper around the REST API provided by BlockApps in order to facilitate
the development of Dapps to be run on a JVM environment.

## Usage

Add to your pom:

```xml
<dependency>
  <groupId>xyz.jetdrone</groupId>
  <artifactId>blockapps.ba</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

In your Application verticle create an instance of the API you'd like to use:

```java
var strato = StratoAPI.create(vertx, "http://localhost/strato-api");

// get the last 15 transactions
strato.getLastTransaction(15, res -> {
  if (res.succeeded()) {
    System.out.println(res.result().encodePrettily());
  }
});
```

This library is polyglot, which means that you can use any of the alternatives:

* Kotlin
* Rx
* Rx2
* Groovy
* Ruby
* JavaScript

For example to use the RXified API you can do:

```java
StratoAPI.create(vertx, "http://localhost/strato-api")
// get the last 15 transactions
  .rxGetLastTransaction(15)
  .subscribe(json -> {
    System.out.println(json.encodePrettily());
  });
```

## Future improvements

The current API uses raw JSON types, a nice addition would be to apply Schema information to these JSON types.

