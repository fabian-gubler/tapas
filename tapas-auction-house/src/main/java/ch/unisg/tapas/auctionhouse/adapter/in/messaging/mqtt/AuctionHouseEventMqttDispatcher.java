package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEvent;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Dispatches MQTT messages for known topics to associated event listeners. Used in conjunction with
 * {@link ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient}.
 *
 * This is where you can define MQTT topics and map them to event listeners, see
 * {@link AuctionHouseEventMqttDispatcher#initRouter()} and
 * {@link AuctionHouseEventMqttDispatcher#registerTopicAndListener(String, AuctionHouseEventMqttListener)}).
 *
 * This class is only provided as an example to help you bootstrap the project. You are welcomed to
 * change or extend this class as you see fit.
 */
@Component
@Profile("mqtt")
public class AuctionHouseEventMqttDispatcher {
    private final Map<String, AuctionHouseEventMqttListener> router;
    private final ExecutorAddedEventListenerMqttAdapter executorAddedEventListenerMqttAdapter;

    private final AuctionStartedEventListenerMqttAdapter auctionStartedEventListenerMqttAdapter;

    private final BidReceivedEventListenerMqttAdapter bidReceivedEventListenerMqttAdapter;

    public AuctionHouseEventMqttDispatcher(ExecutorAddedEventListenerMqttAdapter executorAddedEventListenerMqttAdapter,
                                           AuctionStartedEventListenerMqttAdapter auctionStartedEventListenerMqttAdapter,
                                           BidReceivedEventListenerMqttAdapter bidReceivedEventListenerMqttAdapter
                                           ) {
        this.router = new Hashtable<>();
        this.executorAddedEventListenerMqttAdapter = executorAddedEventListenerMqttAdapter;
        this.bidReceivedEventListenerMqttAdapter = bidReceivedEventListenerMqttAdapter;
        this.auctionStartedEventListenerMqttAdapter = auctionStartedEventListenerMqttAdapter;

        initRouter();
    }

    public void registerTopicAndListener(String topic, AuctionHouseEventMqttListener listener) {
        router.put(topic, listener);
    }

    private void initRouter() {
        // TODO: The topic and the event listener listed below are just to provide an example
        router.put("ch/unisg/tapas-group-tutors/executors", executorAddedEventListenerMqttAdapter);
        router.put("ch/unisg/tapas/bids", bidReceivedEventListenerMqttAdapter);
        router.put("ch/unisg/tapas/auctions", auctionStartedEventListenerMqttAdapter);
    }

    /**
     * Returns all topics registered with this dispatcher.
     *
     * @return the set of registered topics
     */
    public Set<String> getAllTopics() {
        return router.keySet();
    }

    /**
     * Dispatches an event received via MQTT for a given topic.
     *
     * @param topic the topic for which the MQTT message was received
     * @param message the received MQTT message
     */
    public void dispatchEvent(String topic, MqttMessage message) {
        AuctionHouseEventMqttListener listener = router.get(topic);
        listener.handleEvent(message);
    }
}
