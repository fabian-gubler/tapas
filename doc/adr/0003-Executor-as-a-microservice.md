# 3. Executor as a microservice

Date: 2022-10-03

## Status

Accepted

## Context

The roster can select and prompt an executor to carry out a task. After the executor gets the task, he performs the task and returns the result. We don’t expect executors need their own data storage.
We don't know in advance how long the task-execution will take.

## Decision

We decide to use a microservice architecture for executors. 
The executors need to function as independent services, if one executer is down the rest of the system must not be impacted.
Executors will execute task asynchronously and take care of queueing multiple tasks.

## Consequences

If an executor fails, the rest of the system and other executors will still work as it runs separately. Executors are easy to deploy and easy to test.
