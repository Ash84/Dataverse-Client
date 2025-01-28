# Dataverse API bindings project

This project is a Java wrapper around the [Entrepôt Recherche Data Gouv API](https://guides.dataverse.org/en/latest/api/) (formerly Data INRAE's API).
It was initially contributed by [ResearchSpace](www.researchspace.com) in October 2016, and the [main branch](https://github.com/IQSS/dataverse-client-java) is maintained by Richard Adams([otter606](https://github.com/otter606), [richarda23](https://github.com/richarda23)).
Later contributions around support of [Entrepôt Recherche Data Gouv](https://entrepot.recherche.data.gouv.fr/) (formerly Data INRAE) from L. Tromel, Agroclim, INRAE.

## Building 

### Dependencies 

This project requires Java 8 minimum to compile and run.

It is built and tested on Java 11 and Java 17.

It also uses Spring-web (to provide low-level HTTP request/response parsing.) 

The Sword client library is included in this project as a jar file, as it is not available
 in a public maven repository.

### Gradle 

This project is built using Gradle.

You must setup the following properties in `gradle.properties` with theses keys:

```
# the authorized user on archiva server
archivaUser=XXXX
# the user's password
archivaPassword=XXXX
# archiva URLs
archivaInternal=http://archiva:9090/archiva/repository/internal/
ParchivaSnapshots=http://archiva:9090/archiva/repository/snapshots/
```

You can build straight away without needing to install anything:

    ./gradlew clean build -x integrationTest
   
which will compile, run unit tests (but not integration tests) and build a jar file.

### Running integration tests

Integration tests require a connection to a Dataverse instance.
In order to connect to a Dataverse for running tests, the following configuration is set up in `test.properties`.

	dataverseServerURL=https://demo.recherche.data.gouv.fr/
	dataverseAlias=<any collection with which the token's associated account has necessary rights>
	
We recommend you to create a collection with the token's associated account, so the client will have full right on it. 
    
As a minimum, you'll need to specify an API key on the command line to run the tests:

    ./gradlew clean integrationTest -DdataverseApiKey=xxx-xxxx-xxxx

You can also override the Dataverse server URL and Id with your own settings by setting them on the command line:

    ./gradlew clean integrationTest -DdataverseServerURL=https://my.dataverse.org -DdataverseApiKey=xxx-xxx-xxx -DdataverseAlias=MY-DEMO-DATAVERSE

Make sure that almost one dataset is in the Dataverse.

### Installing into a Maven repository

Install the .jar into your .m2 directory:

`gradlew publishToMavenLocal --refresh-dependencies`

then add to your pom.xml:

```xml
	<dependency>
	    <groupId>com.researchspace</groupId>
	    <artifactId>dataverse-client-java</artifactId>
	    <version>1.0.3</version>
	</dependency>
```

Or, you can run:

    ./gradlew clean install
    
to install into a local repository and generate a pom.xml file for calculating dependencies.

### Publishing to a distant archiva repo with current configuration

Then run
`gradlew publish --refresh-dependencies`

## Usage

The best way to explore the bindings currently is by examining integration tests, especially those extending from `AbstractIntegrationTest`.

Very briefly....

```java
    DataverseAPI api = new DataverseAPIImpl();
    //must set in serverURL and apiKey first.
    DataverseConfig config = new DataverseConfig(serverURL, apiKey, dataverseAlias);
    api.configure(config);
    // now you can call
    api.getDataverseOperations().getDataverseById(dataverseAlias);
```

Searching uses a builder pattern to build a search query:

```java
    SearchOperations searchOps = dataverseAPI.getSearchOperations();
    SearchConfig cfg = searchOps.builder().q(FILE_SEARCH_TERM)
				.sortBy(SortBy.date)
				.sortOrder(SortOrder.asc)
				.showFacets(true)
				.showRelevance(true)
				.start(1)
				.perPage(3)
				.build();
    DataverseResponse<SearchResults<Item>> results = searchOps.search(cfg);
```

### Synchronisation and thread-safety

There is no explicit synchronisation performed in this library. The Dataverse configuration is stored in the 
internal state of  implementation classes, so new instances of `DataverseAPIImpl` should be used for each request if running in a multi-threaded environment connecting to different Dataverses.

## Developing

This project makes use of [Project Lombok](https://projectlombok.org) which greatly speeds up the development of POJO classes to wrap JSON data structures. 

There are [instructions](https://projectlombok.org/features/index.html) on how to add it to your IDE.

### Coding standards

Please make sure tests pass before committing, and to add new tests for new additions.

## Progress

API | Endpoint | URL | Implemented ?| Notes 
------|----------|-----|--------------|-------
Native|Dataverses | POST `api/dataverses/$id` | Y| - 
| -   | -         | GET `api/dataverses/$id` | Y | -
| -   | -         | GET `api/dataverses/$id/contents` | Y | -
| -   | -         | DELETE `api/dataverses/$id` | Y | -
| -   | -         | POST `api/dataverses/$id/datasets` | Y | -
| -   | -         | POST `api/dataverses/$identifier/actions/:publish` | Y | -
Native|Datasets | POST `api/dataverses/$id` | Y| -
| -   | -         | GET `api/datasets/$id` | Y | -
| -   | -         | DELETE `api/datasets/$id` | Y | -
| -   | -         | GET `api/datasets/$id/versions` | Y | -
| -   | -         | GET `PUT api/datasets/$id/versions/:draft?` | Y | -
| -   | -         | POST `PUT api/datasets/$id/actions/:publish?type=$type` | Y | -
Native|MetadataBlocks | GET ` api/metadatablocks` | Y| -
| -   | -         | GET ` api/metadatablocks/$identifier` | Y| -
Search | - | GET `api/search` | In progress | All query params supported, optional data not returned yet.
Sword | Upload file | 'Add files to a dataset with a zip file' | Y | -
