# 5. Auction house as a microservice

Date: 2022-10-03

## Status

Accepted

## Context

The roster keeps track of what tasks need to be completed and assigns suitable
executors to the tasks. In case no suitable internal executor was found, we can look for
an external executor in the auction house. Executors can bid for tasks they want to complete and a winner is determined in the auction house.

## Decision

The auction house will be implemented as a separate microservice.

## Consequences

This enables the roster and auction house to scale differently. Furthermore, the auction process is not instantaneous and the search for a suitable external executor is an asynchronous procedure. Increased latency due to communication between different microservices. In addition, both microservices need to ensure that the executors are synchronized. 
