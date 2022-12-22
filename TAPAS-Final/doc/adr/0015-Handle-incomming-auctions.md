# 14. handle incoming auctions

Date: 2022-11-26

## Status

Accepted

## Context.

The Auction House serves as an intersection with the other groups' projects and serves two purposes:
1. if a task cannot be executed by our internal executors, an auction is started and other groups bid on the task
if they have a suitable executor for it.
2. when an auction is started by another group, we receive a notification and our auction house will bid on the auction if we have
   have an internal executor who can perform the task.

In this ADR, the second purpose is defined.


## Decision

Once our auction house detects a new auction (either via MQTT or WebSubb), it will place a bid if the auctioned task can be
can be executed by an internal executor. When we win an auction, the auction house that bought the task at auction creates a new shadow task in our task list.
The shadow task goes through our application workflow like a normal internal task, with the only exception beeing that the original task list,
as well as our own, needs to be updated if the task changes in any way (status updates, output data).
The roster takes care of patching the task in all the required task lists, and as defined by the Interoperability Task Force, new attributes are
added to the task representation, including the original task URI. The task list will check if the task is from an auction
and sends updates to the original task list as appropriate.

## Consequences
Only minor changes are required in our code base, as the workflow remains largely the same and only the patching
to the original task list needs to be implemented.
