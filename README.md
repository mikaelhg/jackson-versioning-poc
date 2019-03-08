# Jackson API versioning POC

To demonstrate that creating versioned JSON APIs with Jackson is possible.

`ObjectMapper` is modified with a `SerializationModifier` and a `DeserializationModifier`,
which react to the presence and contents of a custom `Version` annotation in our bean classes.

```text
p -> om(1, 0, 0) -> json: {"name":"Muffin Man","address":"Drury Lane"}
p -> om(1, 1, 0) -> json: {"name":"Muffin Man","address":"Drury Lane","sport":"Boxing"}
p -> om(1, 2, 0) -> json: {"name":"Muffin Man","address":"Drury Lane","sport":"Boxing","claimToFame":"Match Fixing"}
p -> om(1, 2, 0) -> om(1, 1, 0) -> p: Person(name="Muffin Man", address="Drury Lane", sport="Boxing", claimToFame="null")
```

```java
public class Person {

    public String name;

    @Version(major = 1, minor = 0, patch = 0)
    public String address;

    @Version(major = 1, minor = 1, patch = 0)
    public String sport;

    @Version(major = 1, minor = 2, patch = 0)
    public String claimToFame;

    /* ... */

}
```

Like so:

```java
final var person = new Person("Muffin Man", "Drury Lane", "Boxing", "Match Fixing");
final var om100 = new VersioningObjectMapper(1, 0, 0);
final var s100 = om100.writeValueAsString(person);
```
