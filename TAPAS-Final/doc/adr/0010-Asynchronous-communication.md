# 10. Asynchronous communication
Date: 2022-10-21

## Status
Accepted

## Context
The microservices of our application needs to communicate with each other to share data and make requests. 

## Decision
We will use asynchronous communication to communicate between the different microservices where it makes sense e.g. if the execution times is long we don't want to unnecessarily block other services.
Asynchronous communication allows a high level of decoupling which in term strengthens our fault tolerance and improves our scalability. Both of these characteristics were found critical for our app.
Asynchronous requests allow us to minimise temporary quantum entanglement and allows us to keep our microservices as self-sufficient as possible.
The communication between the tasklist, roster & the individual executors is implemented asynchronous.

## Consequences
Asynchronous communication will make our application as a whole more complex to build and debug.