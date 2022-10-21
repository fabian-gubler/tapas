# 12.  Shared library for code reuse
Date: 2022-10-21

## Status
Accepted

## Context
Our application does not have a lot of shared code at the moment. Should the amount of shared code increase, we will need a strategy to handle shared code the options are to use a shared library or create a shared data service.

## Decision
We will use a shared library to implement common logic that is needed in multiple services. A shared library gives us the security of compile time errors while reducing runtime errors. This will strengthen the overall stability of our application.

## Consequences
The shared library adds a dependencies to our project wich we will have to manage. We will have to pay special attention to the versioning of our shared library.