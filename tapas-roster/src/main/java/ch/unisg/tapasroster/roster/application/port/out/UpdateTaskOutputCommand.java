package ch.unisg.tapasroster.roster.application.port.out;


import ch.unisg.tapasroster.roster.domain.RosterAssignment;

public class UpdateTaskOutputCommand {
    public String output;

    public RosterAssignment.TaskLocation taskLocation;

    public UpdateTaskOutputCommand(String output, RosterAssignment.TaskLocation taskLocation) {
        this.output = output;
        this.taskLocation = taskLocation;
    }
}
