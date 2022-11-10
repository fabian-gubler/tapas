package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.application.port.out.bids.PlaceBidForAuctionPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class is a template for implementing an HTTP adapter that places bid.
 */
@Component
public class PlaceBidForAuctionHttpAdapter implements PlaceBidForAuctionPort {
    private static final Logger LOGGER = LogManager.getLogger(PlaceBidForAuctionHttpAdapter.class);

    @Override
    public void placeBid(Auction auction, Bid bid) {
        // TODO: send HTTP request to place bid
    }
}
