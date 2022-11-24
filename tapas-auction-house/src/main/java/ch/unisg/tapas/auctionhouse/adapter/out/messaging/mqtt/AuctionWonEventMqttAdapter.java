package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.auctions.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.AuctionWonEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is a template for sending auction won events via MQTT. This class was created here only
 * as a placeholder, it is up to you to decide how such events should be sent.
 */

/*
@Component
@Profile("mqtt")
public class AuctionWonEventMqttAdapter implements AuctionWonEventPort {
    @Override
     public void publishAuctionWonEvent(AuctionWonEvent event, Auction.AuctionedTaskUri auctionedTaskUri) {
        TapasMqttClient mqttClient = TapasMqttClient.getInstance();

        try {
            mqttClient.publish("ch/unisg/tapas/auctionwon", BidJsonRepresentation.serialize(null));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}*/
