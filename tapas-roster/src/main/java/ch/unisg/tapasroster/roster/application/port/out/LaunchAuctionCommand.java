package ch.unisg.tapasroster.roster.application.port.out;

import lombok.Getter;
import lombok.Setter;

public class LaunchAuctionCommand {

    public static final String MEDIA_TYPE = "application/json";


    @Getter @Setter
    private String taskUri;

    @Getter @Setter
    private String taskType;

    @Getter @Setter
    private Integer deadline;

    public LaunchAuctionCommand(String taskUri, String taskType, Integer deadline) {
        this.taskUri = taskUri;
        this.taskType = taskType;
        this.deadline = deadline;
    }

}
