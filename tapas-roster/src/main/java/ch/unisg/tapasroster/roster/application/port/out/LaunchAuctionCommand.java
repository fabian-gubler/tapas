package ch.unisg.tapasroster.roster.application.port.out;

import lombok.Getter;
import lombok.Setter;

public class LaunchAuctionCommand {

    public static final String MEDIA_TYPE = "application/json";

    @Getter @Setter
    private String auctionID;

    @Getter @Setter
    private String auctionHouseUri;

    @Getter @Setter
    private String taskUri;

    @Getter @Setter
    private String taskType;

    @Getter @Setter
    private Integer deadline;

    @Getter @Setter
    private String auctionStatus;

    public LaunchAuctionCommand(String auctionID, String auctionHouseUri, String taskUri, String taskType, Integer deadline, String auctionStatus ) {
        this.auctionID = auctionID;
        this.auctionHouseUri = auctionHouseUri;
        this.taskUri = taskUri;
        this.taskType = taskType;
        this.deadline = deadline;
        this.auctionStatus = auctionStatus;
    }

}
