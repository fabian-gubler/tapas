# 14. Hadle incomming auctions

Date: 2022-11-26

## Status

Accepted

## Context

The auction house serves as the intersection to the projects of the other groups and serves two purposes:
1. If a Task can not be executed by our internal executors an auction will be launched and other groups will bid on the task
if they have a suitable executor for it.
2. If an auction is launched by another group we will receive a notification and our auction house will bid on the auction if we
   have an internal executor that can execute the given task.

In this ADR the first purpose is defined.


## Decision

If the roster receives a task for which it can not find a suitable executor it will send the task information to the auction house which will
then launch the auction. The auction house handles incoming bids an determines a winner.
Once an auction is won the auction house will place a shadow task in the task list of the winning group according to the specification developed by the Interoperability Task Force.
Because not all the task information is sent to the auction house when an auction is launched the auction house will first fetch the 
complete task from the task list before sending it to the recipients task list.

## Consequences
Because the auction house does not have a complete copy of the task an additional request to the task list is needed.

 
