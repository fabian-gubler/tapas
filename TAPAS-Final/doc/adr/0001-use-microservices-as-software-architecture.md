# 1. Use Microservices as software architecture

Date: 2022-09-26

## Status

Accepted

## Context

We need a distributed architecture because we need to have a collection of independently deployable components.
Based on our chosen architecture characteristics interoperability, fault-tolerance and scalability we evaluated an event-driven system or microservices.
We chose microservices because of lower complexity which leads to less developement overhead.

## Decision

We propose to user a microservice architecture

## Consequences

Our system gets more agile and scalable and is easier to extend.
The workflow will be more complex and remote method calls will impact performance.