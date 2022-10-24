package ch.unisg.tapasroster.roster.application.port.out;


import ch.unisg.tapasroster.roster.domain.Roster;

public class UpdateTaskOutputCommand {
    public String output;

    public Roster.TaskLocation taskLocation;

    public UpdateTaskOutputCommand(String output, Roster.TaskLocation taskLocation) {
        this.output = output;
        this.taskLocation = taskLocation;
    }
}
