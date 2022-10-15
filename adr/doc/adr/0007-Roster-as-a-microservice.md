# 7. Roster as a microservice

Date: 2022-10-03

## Status

Accepted

## Context
The roster keeps track of the internal task executions. The roster receives tasks from the task list. If the task can be executed internally the roster puts it in the queue and keeps the status of the task. If the task cannot be executed internally it is forwarded to the auction house. The executor pool and roster could be implemented together or as two separate services.

## Decision

We’ll create two separate services as this will improve our system’s fault tolerance.

## Consequences

The list of executors must be synchronized between the roster and executor pool microservices. Furthermore, by adding interservice communication we increase latency.

