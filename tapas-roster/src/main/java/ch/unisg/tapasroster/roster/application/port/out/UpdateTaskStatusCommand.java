package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.Roster;

public class UpdateTaskStatusCommand {

    public enum Status {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    public Status taskStatus;

    public Roster.TaskLocation taskLocation;

    public UpdateTaskStatusCommand(Status taskStatus, Roster.TaskLocation taskLocation) {
        this.taskStatus = taskStatus;
        this.taskLocation = taskLocation;
    }
}
