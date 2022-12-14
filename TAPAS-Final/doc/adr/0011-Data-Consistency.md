# 11. Data consistency
Date: 2022-10-21

## Status
Accepted

## Context
Since we choose a single ownership approach for our app (ADR 8) data that is processed by multiply services need to be synchronised to ensure data consistency.

## Decision
We decided to use BASE transaction instead of ACID database transactions. 
Data changes in one Microservices will lead to a temporary inconsistent state between the data in the microservices (e.g. a task gets delete which is already being processed by an executor).  
To achieve eventual data consistency we choose an event based synchronisation. 
Event based synchronisation allows us to secure an eventual consistent state across our microservices with durable subscribers so that the data change will be propagated even if a service is temporarily unavailable.

## Consequences
A workflow event patter need to be considered for the case that a request succeeds at the initial Microservices but then fails in another Microservices down the linke which is informed via the event system.
