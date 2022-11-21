package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionHouseEventMqttDispatcher;
import ch.unisg.tapas.auctionhouse.application.port.out.bids.PlaceBidForAuctionPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.common.ConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is a template for implementing an MQTT adapter that places bid.
 */
@Component
@Profile("mqtt")
@RequiredArgsConstructor
public class PlaceBidForAuctionMqttAdapter implements PlaceBidForAuctionPort {
    private static final Logger LOGGER = LogManager.getLogger(PlaceBidForAuctionMqttAdapter.class);

    @Override
    public void placeBid(Auction auction, Bid bid) {
        TapasMqttClient mqttClient = TapasMqttClient.getInstance();

        try {
            mqttClient.publish("ch/unisg/tapas/bids", BidJsonRepresentation.serialize(bid));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
