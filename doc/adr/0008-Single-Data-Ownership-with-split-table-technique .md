# 8. Single Data Ownership with split table technique



## Status



Accepted



## Context

Because we decided to user microservices for each bounded context we have data that is used by different services.


## Decision

We decide to use single ownership for the data. Each microservice owns its own data. 
In the case that multiple serves need similar information we implement a split table technique.
Single data ownership simplifies our implementation and deployment.
Our goal is to keep the shared data at the minimum.


## Consequences


Single data ownership preserves out bounded context but requires data synchronization.

The microservices are less coupled in terms of data storage. Each microservice can be deployed independently and scale differently. 