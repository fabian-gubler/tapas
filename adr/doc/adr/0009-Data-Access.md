# 9. Data Access



## Status



Accepted



## Context



Data can either be made directly accessible to the microservice by opting for continuous column replication or caching or indirectly made available by requesting it from another service. To do this we can use either the Interservice Communication Pattern, the Column Schema Replication Pattern, the Replicated Caching Pattern, or the Data Domain Pattern.



## Decision

We decided to use the interservice communication pattern. Distributed caching would be a good solution, but is too complex for our use case.



## Consequences



Interservice requests add more latency. Interservice communication patterns allows us to keep our microservices as lean as possible, contrarily with distributed caching, the bigger the project, the more caching space we need. 