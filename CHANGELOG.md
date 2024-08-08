Significant changes since 0.1.0

1.0.2 2024-08-08

New adaptations to ERDG, some fields properties have been updated.

1.0.1 2023-01-04

Adaptations to ERDG

 - feature: All external requests should now throw RestClientException on failure with a reason
 - feature: Added UsersOperations to handle token expiration date.

1.0.0 2022-11-21

Increasing major version due to major updates to dependencies. However, there are no
breaking API changes in this library. 

- dependencies: Major dependency updates to Spring 5.3, Lombok 18.24. 
- build: fix integration tests
- build: enable integration test running through Github actions
- build: test build and test on Java 8, 11, and 17.

0.2.0 2022-11-20

- build:  update gradlew to use gradle 7.5
- build:  Basic Github action to run tests on pull request
- dependency: update Lombok from 1.16 to 1.18.4 to enable building on Java 11
- feature: #12 overloaded method for uploadFile

