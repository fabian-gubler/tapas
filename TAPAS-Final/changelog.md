# TAPAS Group 3 Changelog

## Tapas-group3 v0.0.6
December 4th 2022

### Features

#### Web of things executors added
- New executors have been added, executors query hypermedia database to execute actions
- Robot executor added, executor added that can move cherrybot robot to a predefined position
- Miro executor added, executor added that requests current sensor readings of mirocard over CoAP gateway

### Bug fixes
- A bug relating to the patching of shadow tasks has been fixed
- A bug in the crawling algorithm for discovering new auction houses has been fixed

### Architectural Decision Records
- ADRs have been updated to match current release cycle
- ADRs can be found in the `/doc/adr` directory


## Tapas-group3 v0.0.5
December 4th 2022

### Features

#### Semantic hypermedia overlay
- a semantic hypermedia overlay has been defined with the other groups to allow auction-feed discovery
- link headers have been updated in the auction house to reflect the semantic hypermedia overlay

#### Hypermedia based discovery of auction houses
- a basic crawling algorithm has been implemented to discover auction feeds

### Bug fixes
- the patching of shadow tasks has been corrected
- a bug regarding the time an auction is available has been fixed

### Architectural Decision Records
- ADRs have been updated to match current release cycle
- ADRs can be found in the `/doc/adr` directory


## Tapas-group3 v0.0.4
November 26th 2022

### Features

#### Uniform Interaction with HTTP 
- integrated the auction house with the rest of the system
- defined a uniform HTTP API for the auction house with the interoperability task force

#### Event-based Interactions
- created an open and decentralized market place with W3C WebSub
- able to publish new auctions for other subscribed groups
- able to handle auctions and bids via MQTT

### Tasks still to be completed
- Patching the tasks in our own task list after the task is done by other groups
- change the status of the task, which was sent to our auction house afterwards in our task list

### Architectural Decision Records
- ADRs have been updated to match current release cycle
- ADRs can be found in the `/adr/doc/adr` directory


## Major changes over the break

### Performance
- General performance improvement through asynchronous request
	- Asynchronous requests have been implemented between the tasklist and the roster
	- Asynchronous requests have been implemented between the executors and the roster
* General improvement to the fault tolerance of the app through improved error handling

### ADR rework
* ADRs 1, 2, 3, 4, 6 and 10 have been reworded to better clarify the decisions made

### New Executor
* Joke Executor has been changed from joke-api to gender-api


## Tapas-group3 v0.0.2
October 15th 2022

### Features

- **Data Persistence**: Data Persistence implemented for tasks and executor-pool microservices using hexagonal architecture
- **Mocking**: Covered two use cases using the Mockito libary following a Behavior-driven development approach
- **Web Adapter Testing**:  Integration tests written for two web adapters testing the creation of executor pools and matching executor to received task

### Bugs
- **Persistence Adapter Testing**: Currently facing issues to connect to MongoDB for Integration tests written to test data persistence regarding the executor pool.

### Architectural Decision Records
- ADRs have been updated to match current release
- ADRs can be found in the `/adr/doc/adr` directory


## Tapas-group3 v0.0.1 
October 8th 2022 
This is the initial Release of Tapas.

#### Whats new:
##### Executorpool service – v0.0.1
* Functionality to add and remove executors
* Basic matching between tasks and executors

##### Executor-compute service – v0.0.1
* Basic computation functionality (add, subtract, multiply, divide)

##### Executor-joke service - v0.0.1
* Retrieve random Chuck Norris Jokes

##### Architectural Decision Records
* Adrs can be found in the `/adr/doc/adr` directory


### Bug fixes:
No bugs have been fixed in this release

