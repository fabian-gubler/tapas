# 10. Asynchronous communication
Date: 2022-10-21

## Status
Accepted

## Context
The microservices of our application need to communicate with each other to share data and make requests. 

## Decision
We will use asynchronous communication to communicate between the different microservices.
Asynchronous communication allows a high level of decoupling which in term strengthens our fault tolerance and improves our scalability. Both of these characteristics were found critical for our app.
Asynchronous requests allow us to minimise temporary quantum entanglement and allows us keep our micro-services as self sufficient as possible.

## Consequences
Asynchronous communication will make our application as a whole more complex to build and debug.