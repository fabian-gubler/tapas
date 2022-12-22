# 4. Tasklist as a microservice

Date: 2022-10-03

## Status

Accepted

## Context

The tasks created by the users are stored in the tasklist. 
The tasklist forwards the tasks to the roster for matching with executors.
The tasks remain in the tasklist and get updated by the roster until they are deleted.

## Decision

We decide to implement the tasklist as an independent microservice.
The tasklist traffic can vary over time and is not predictable. Therefore, the tasklist has to perform under heavy load. This leads to good scalability and elasticity.
If the tasklist fails, other services should not be impacted.

## Consequences

The tasklist can scale up under heavy traffic. As the performance of the system should degrade gracefully in the presence of heavy load we decided to separate the main inlet of traffic as a microservice. This allows the service to scale independently of the remaining services of our application.
