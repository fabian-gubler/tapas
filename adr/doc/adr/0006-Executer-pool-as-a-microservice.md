# 6. Executer pool as a microservice

Date: 2022-10-03

## Status

Accepted

## Context

The purpose of the executer pool is to keep track of which executors are available and what type of tasks they can execute.

## Decision

Executor pool is implemented as a separate microservice.

## Consequences

Increased latency due to communication between different microservices. Increase complexity because both services need to keep track of available executors. 
