package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.application.port.out.auctions.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionWonEvent;
import org.springframework.stereotype.Component;

/**
 * This class is a template for an HTTP adapter that sends auction won events. This class was created
 * here only as a placeholder, it is up to you to decide how such events should be sent.
 */
@Component
public class AuctionWonEventHttpAdapter implements AuctionWonEventPort {
    @Override
    public void publishAuctionWonEvent(AuctionWonEvent event) {
        // TODO: send request to notify the auction was won
    }
}
