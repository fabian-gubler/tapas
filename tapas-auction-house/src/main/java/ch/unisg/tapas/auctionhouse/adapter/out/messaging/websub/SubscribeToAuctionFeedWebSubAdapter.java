package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.feeds.SubscribeToAuctionFeedPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("websub")
public class SubscribeToAuctionFeedWebSubAdapter implements SubscribeToAuctionFeedPort {
    private static final Logger LOGGER = LogManager.getLogger(SubscribeToAuctionFeedWebSubAdapter.class);

    @Override
    public void subscribeToFeed(Auction.AuctionFeedId feedId) {
        LOGGER.info("Subscribing to auction feed via WebSub: " + feedId.getValue());

        // TODO: Subscribe to the auction feed via WebSub
        // 1. Send a request to retrieve the auction feed in order to discover the WebSub hub to subscribe to.
        // 2. Send a subscription request to the discovered WebSub hub to subscribe to events relevant
        // for this auction house.
        // 3. [DONE] Handle the validation of intent from the WebSub hub in (cf. WebSub protocol).
        // This step is already implemented by the REST controller defined in:
        // ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub.WebSubIntentVerificationListener
        //
        // Once the subscription is activated, the hub will send "fat pings" with content updates.
        // The content received from the hub will depend primarily on the design of the Auction House
        // HTTP API.
        //
        // For further details see:
        // - W3C WebSub Recommendation: https://www.w3.org/TR/websub/
        // - the implementation notes of the WebSub hub you are using to distribute events
    }
}
