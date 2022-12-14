# Tapas-group3 v0.0.3
October 24th 2022

## Features

### Tapas Roster
- Roster has been implemented to keep track of internal task executions
- Matching of tasks with executors
- Update tasklist (with Roster) upon completion of particular task

### Testing
- Architecture Tests: Dependency Rules for Executor-Pool and Roster
- System Tests: Added test to cover the integration of services  to evaluate the system's compliance with its specified requirements

## Tasks still to be completed
- Roster puts task in queue and keeps status of tasks (given the task is possible to be internally executed)
- Task can be forwarded to an auction house (if not being able to be executed internally)

## Bugs
- Persistence issues on Virtual Machine (MongoDB)
- Performance issues on Virtual Machine (Unreliable when Performance testing)

## Architectural Decision Records
- ADRs have been updated to match current release cycle
- Some ADRs have not yet been implemented (in particular: shared library, async communication)
- ADRs can be found in the `/adr/doc/adr` directory
