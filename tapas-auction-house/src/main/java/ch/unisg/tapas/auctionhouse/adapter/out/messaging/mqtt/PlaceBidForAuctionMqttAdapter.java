package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.port.out.bids.PlaceBidForAuctionPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is a template for implementing an MQTT adapter that places bid.
 */
@Component
@Profile("mqtt")
public class PlaceBidForAuctionMqttAdapter implements PlaceBidForAuctionPort {
    private static final Logger LOGGER = LogManager.getLogger(PlaceBidForAuctionMqttAdapter.class);

    @Override
    public void placeBid(Auction auction, Bid bid) {
        LOGGER.info("Sending bid via MQTT");
        // TODO: send MQTT request to place bid
    }
}
