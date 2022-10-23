# 8. Single Data Ownership with split table technique



## Status



Accepted



## Context



Some services need access to the same data.


## Decision



We decide to use single ownership for the data. Each microservice owns its own data. In the case that multiple serves need similar information we implement a split table technique.



## Consequences



Single data ownership preserves out bounded context but requires data synchronization.

The microservices are less coupled in terms of data storage. Each microservice can be deployed independently and scale differently. 