# 6. Executer pool as a microservice

Date: 2022-10-03

## Status

Accepted

## Context

The purpose of the executer pool is to keep track of which executors are available and what type of tasks they can execute.

## Decision

Executor pool is implemented as a separate microservice.
We need to be able to add exeutors even if other services are down.
The executor pool will experience less load compared to the tasklist and therefore scale differently. Due to that it makes sense to separate them into different services.

## Consequences

Increased latency due to communication between different microservices. Increase complexity because both services need to keep track of available executors. 
