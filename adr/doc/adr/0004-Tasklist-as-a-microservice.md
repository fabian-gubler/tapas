# 4. Tasklist as a microservice

Date: 2022-10-03

## Status

Accepted

## Context

The tasks created by the users are stored in the tasklist. The tasklist forwards the tasks for matching with executors. Tasks remain in the tasklist until they're deleted.

## Decision

We decide to use a microservice architecture for the tasklist.

## Consequences

The tasklist can scale up under heavy traffic.  As the performance of the system should degrade gracefully in the presence of heavy load we decided to separate the main inlet of traffic as a
microservice which allows the service to scale independently from the remaining services of our application.  
