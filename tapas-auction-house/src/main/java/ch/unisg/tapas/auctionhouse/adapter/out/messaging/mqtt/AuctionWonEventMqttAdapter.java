package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.port.out.auctions.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionWonEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is a template for sending auction won events via MQTT. This class was created here only
 * as a placeholder, it is up to you to decide how such events should be sent.
 */
@Component
@Profile("mqtt")
public class AuctionWonEventMqttAdapter implements AuctionWonEventPort {
    @Override
    public void publishAuctionWonEvent(AuctionWonEvent event) {
        // TODO: publish auction won event via MQTT
    }
}
