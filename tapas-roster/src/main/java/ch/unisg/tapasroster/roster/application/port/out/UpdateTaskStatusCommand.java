package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.RosterAssignment;

public class UpdateTaskStatusCommand {

    public enum Status {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    public Status taskStatus;

    public RosterAssignment.TaskLocation taskLocation;

    public UpdateTaskStatusCommand(Status taskStatus, RosterAssignment.TaskLocation taskLocation) {
        this.taskStatus = taskStatus;
        this.taskLocation = taskLocation;
    }
}
