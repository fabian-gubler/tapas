package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

/**
 * Used to expose a representation of the state of an auction through an interface.
 * <p>
 * Authors: Tapas Group 3 #goMCS #overachieve
 */
public class BidJsonRepresentation {
    public static final String MEDIA_TYPE = "application/bid+json";

    @Getter
    @Setter
    private String auctionId;

    @Getter
    @Setter
    private String bidderTaskListUri;

    @Getter
    @Setter
    private Integer bidTime;

    @Getter
    @Setter
    private String bidderName;

    @Getter
    @Setter
    private String bidderAuctionHouseUri;


    public BidJsonRepresentation() {
    }

    public BidJsonRepresentation(String auctionId, String bidderTaskListUri, Integer bidTime, String bidderName, String bidderAuctionHouseUri) {
        this.auctionId = auctionId;
        this.bidderTaskListUri = bidderTaskListUri;
        this.bidTime = bidTime;
        this.bidderName = bidderName;
        this.bidderAuctionHouseUri = bidderAuctionHouseUri;
    }

    public BidJsonRepresentation(Bid bid) {
        this.auctionId = bid.getAuctionId().getValue();
        this.bidderTaskListUri = bid.getBidderTaskListUri().getValue().toString();
        this.bidTime = bid.getBidTime().getValue();
        this.bidderName = bid.getBidderName().getValue();
        this.bidderAuctionHouseUri = bid.getBidderAuctionHouseUri().getValue().toString();
    }

    public static String serialize(Bid bid) throws JsonProcessingException {
        BidJsonRepresentation representation = new BidJsonRepresentation(bid);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(representation);
    }

    public static Bid deserialize(String bidString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(bidString);

        BidJsonRepresentation representation = mapper.treeToValue(data, BidJsonRepresentation.class);

        return new Bid(
            new Auction.AuctionId(representation.getAuctionId()),
            new Bid.BidderName(representation.getBidderName()),
            new Bid.BidderAuctionHouseUri(URI.create(representation.getBidderAuctionHouseUri())),
            new Bid.BidderTaskListUri(URI.create(representation.getBidderTaskListUri())),
            new Bid.BidTime(representation.getBidTime())
        );
    }
}
