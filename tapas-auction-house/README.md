# tapas-auction-house

The Auction House is the part of your TAPAS application that is largely responsible for the interactions
with the TAPAS applications developed by the other groups. More precisely, it is responsible for
launching and managing auctions for tasks that overcome the capabilities of your own executors. The
Auction House is implemented following the Hexagonal Architecture (based on examples from book "Get
Your Hands Dirty on Clean Architecture" by Tom Hombergs).

Technologies: Java, Spring Boot, Maven

**Note:** this repository contains an [EditorConfig](https://editorconfig.org/) file (`.editorconfig`)
with default editor settings. EditorConfig is supported out-of-the-box by the IntelliJ IDE. To help maintain
consistent code styles, we recommend to reuse this editor configuration file in all your services.

## Project Overview

This project provides a partial implementation of the Auction House. The main features include (see
code documentation for more details):
* starting an auction using a command via an HTTP adapter (see sample request below)
* running and managing auctions:
  * each auction has a deadline by which it is open for bids
  * once the deadline has passed, the auction house closes the auction and selects a random bid as winner
* retrieving the list of open auctions via an HTTP adapter, i.e. auctions accepting bids (see sample
  request below)
* retrieving the representation of an auction via an HTTP adapter (the auction may be closed)
* receiving events when executors are added to the TAPAS application (both via HTTP and MQTT adapters)
* receiving events when new auctions are launched by other Auction Houses (part of the assignment)
* automated placement of bids in open auctions: the auction house will place a bid in every open
  auction it learns about if there is at least one executor that can handle the type of task
  being auctioned
* discovery of other auction houses via a provided resource directory (see assignment sheet for
  Exercises 7 & 8 for more details)

## Overview of Adapters

In addition to the overall skeleton of the auction house, the current partial implementation provides
several adapters to help you get started.

### HTTP Adapters

Sample HTTP request for launching an auction:

```shell
curl -i --location --request POST 'https://tapas-auction-house.86-119-34-23.nip.io/auctions/' \
--header 'Content-Type: application/json' \
--data-raw '{
  "taskUri" : "http://example.org",
  "taskType" : "taskType1",
  "deadline" : 10000
}'

HTTP/1.1 201
Location: https://tapas-auction-house.86-119-34-23.nip.io/auctions/1
Content-Length: 0
Date: Wed, 09 Nov 2022 22:58:38 GMT
```

Sample HTTP request for retrieving auctions currently open for bids:

```shell
curl -i --location --request GET 'https://tapas-auction-house.86-119-34-23.nip.io/auctions/'

HTTP/1.1 200
Content-Type: application/json
Content-Length: 307
Date: Wed, 09 Nov 2022 23:04:13 GMT

[
  {
    "auctionId":"2",
    "auctionHouseUri":"https://tapas-auction-house.86-119-34-23.nip.io/",
    "taskUri":"http://example.org",
    "taskType":"taskType1",
    "deadline":10000,
    "status":"OPEN"
  },
  {
    "auctionId":"3",
    "auctionHouseUri":"https://tapas-auction-house.86-119-34-23.nip.io/",
    "taskUri":"http://example.org",
    "taskType":"taskType1",
    "deadline":10000,
    "status":"OPEN"
  }
]
```

Sending an [ExecutorAddedEvent](src/main/java/ch/unisg/tapas/auctionhouse/application/port/in/ExecutorAddedEvent.java)
via an HTTP request:

```shell
curl -i --location --request POST 'https://tapas-auction-house.86-119-34-23.nip.io/executors/taskType1/executor1'

HTTP/1.1 204
Date: Wed, 09 Nov 2022 23:08:59 GMT
```

### MQTT Adapters

Sending an [ExecutorAddedEvent](src/main/java/ch/unisg/tapas/auctionhouse/application/port/in/ExecutorAddedEvent.java)
via an MQTT message via HiveMQ's [MQTT CLI](https://hivemq.github.io/mqtt-cli/):

```shell
 mqtt pub -t ch/unisg/tapas-group1/executors -m '{ "taskType" : "taskType1", "executorId" : "executor1" }'
```

## Event-based Interaction with W3C WebSub and MQTT

### W3C WebSub

In Task 2 of this assignment, you will implement [W3C WebSub](https://www.w3.org/TR/websub/), an HTTP-based
brokered pub/sub protocol. Several WebSub Hubs are available for public use from
[Google](https://websub.appspot.com/), [Superfeedr](https://websub.superfeedr.com/),
and [Switchboard](https://switchboard.p3k.io/) (open-source).  For this assignment, we recommend using
the WebSub hub provided by Google. See more: [https://websub.appspot.com/](https://websub.appspot.com/)

**Tips:**
* for debugging WebSub subscribers, see: [https://websub.appspot.com/subscribe](https://websub.appspot.com/subscribe)
* for debugging WebSub publishers, see: [https://websub.appspot.com/publish](https://websub.appspot.com/publish)

### MQTT

In Task 3 of this assignment, you will implement MQTT.

An easy way to set up a local MQTT broker is with HiveMQ and Docker:
[https://www.hivemq.com/downloads/docker/](https://www.hivemq.com/downloads/docker/)

```shell
docker run -p 8080:8080 -p 1883:1883 hivemq/hivemq4
```

The above command launches a Docker container with a HiveMQT broker and binds to the container on 2 ports:
* port `1883` is used by the MQTT protocol
* port `8080` is used for the HiveMQ dashboard; point your browser to: [http://localhost:8080/](http://localhost:8080/)

To bind the Docker container to a different HTTP port, you can configure the first parameter. E.g.,
this command binds the HiveMQT dashboard to port `8085`:

```shell
docker run -p 8085:8080 -p 1883:1883 hivemq/hivemq4
```

For development and debugging, it might help to install an MQTT client as well. HiveMQ provides an MQTT
Command-Line Interface (CLI) that may help: [https://hivemq.github.io/mqtt-cli/](https://hivemq.github.io/mqtt-cli/)
