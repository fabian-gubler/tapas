package ch.unisg.tapas.auctionhouse.application.handler;

import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEventHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.BidReceivedEventHandler;
import ch.unisg.tapas.auctionhouse.application.port.out.bids.PlaceBidForAuctionPort;
import ch.unisg.tapas.auctionhouse.domain.*;
import ch.unisg.tapas.common.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Handler for bid received started events.
 */
@Component
public class BidReceivedHandler implements BidReceivedEventHandler {
    private static final Logger LOGGER = LogManager.getLogger(BidReceivedHandler.class);

    @Autowired
    private ConfigProperties config;

    @Autowired
    private PlaceBidForAuctionPort placeBidForAuctionPort;

    @Override
    public boolean handleBidReceivedEvent(BidReceivedEvent bidReceivedEvent) {
        Bid bid = bidReceivedEvent.getBid();

        try {
            Auction auction = AuctionRegistry.getInstance().getAuctionById(bid.getAuctionId());
            auction.addBid(bid);
        } catch (AuctionNotFoundError e) {
            System.out.println("No auction found for incoming bid. No further action.");
            throw new RuntimeException(e);
        }

        return true;
    }
}
