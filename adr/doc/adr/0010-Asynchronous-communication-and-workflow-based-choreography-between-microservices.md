# 10. Asynchronous communication and workflow based choreography between microservices
Date: 2022-10-21

## Status
Accepted

## Context
The microservices of our application need to communicate with each other to share data and make requests. To manage communication either a central orchestrated workflow or a distribute choreographic workflow can be implemented.

## Decision
We will use asynchronous communication to communicate between the different microservices.
Asynchronous communication allows a high level of decoupling which in term strengthens our fault tolerance and improves our scalability. Both of these characteristics were found critical for our app.
Asynchronous requests allow us to minimise temporary quantum entanglement and allows us keep our micro-services as self sufficient as possible.

To manage our Workflow we choose and choreography based approach. This again helps us achieve a high decoupling of the services and improves scalability and fault tolerance.

## Consequences
Asynchronous communication will make our application as a whole more complex to build and debug.

Workflow based choreography needs to be managed in a distributed workflow special attention needs to be payed to the state management in each involved Microservices.

#hsg/sem1/asse