## Spring WebFlux
- Spring WebFlux is a reactive web framework introduced in Spring Framework 5.
- It is designed to provide a reactive programming model for building web applications and APIs using Spring.
- Spring WebFlux is built on top of Project Reactor, which is a popular reactive programming library for the JVM.
- Spring WebFlux is supported on Tomcat, Jetty, Servlet 3.1+ containers, as well as on non-Servlet runtimes such as Netty and Undertow.
- All the Rest APIs will be **asynchronous** and will return a **Publisher (Mono or Flux)**.

### Reactive Database Drivers
Some of the supported reactive drivers in Spring WebFlux include:
- **Reactive MongoDB driver:** This driver provides reactive support for MongoDB.
- **Reactive Redis driver:** This driver provides reactive support for Redis, an in-memory key-value store.
- **Reactive Cassandra driver:** This driver provides reactive support for Cassandra.

#### Spring WebFlux supports two types of programming models:
- Traditional annotation-based model with @Controller, @RequestMapping, and other annotations that you have been using in Spring MVC.
- A brand new Functional style model based on Java 8 lambdas for routing and handling requests.

#### WebFluxTest Annotation
- @WebFluxTest annotation used to test Spring WebFlux controllers.
- This annotation creates an application context that contains all the beans necessary for testing a Spring WebFlux controller.
- @WebFluxTest used in combination with @MockBean to provide mock implementations for required collabrators.
- @WebFluxTest also auto-configures WebTestClient, which offers a powerful way to quickly test WebFlux controllers without needing to start a full HTTP server.
