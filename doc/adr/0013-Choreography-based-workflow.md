# 10. Asynchronous communication and workflow based choreography between microservices
Date: 2022-10-21

## Status
Accepted

## Context
To manage communication either a central orchestrated workflow or a distribute choreographic workflow can be implemented.

## Decision

To manage our Workflow we choose and choreography based approach.
This helps us achieve a high decoupling of the services and improves scalability and fault tolerance.
A distributed workflow allows us to achieve good scaling and high fault tolerance since the services function independently.

## Consequences

Workflow based choreography needs to be managed in a distributed workflow and complicates error handling and state management.
