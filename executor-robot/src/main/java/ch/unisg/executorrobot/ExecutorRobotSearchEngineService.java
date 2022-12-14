package ch.unisg.executormiro;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.io.TDGraphReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ExecutorRobotSearchEngineService {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorRobotSearchEngineService.class);

    public static final String SEARCH_ENGINE_ENTRY_POINT = "https://api.interactions.ics.unisg.ch/search/searchEngine";

    public ThingDescription findTd(String sparql_query, String thingTitle) throws ThingDescriptionNotFoundException {

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SEARCH_ENGINE_ENTRY_POINT))
                    .header("content-type", "application/sparql-query")
                    .POST(HttpRequest.BodyPublishers.ofString(sparql_query))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(response.body().toString()));

            // parse xml response and get all URIs
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inStream);
            NodeList nodeList = document.getElementsByTagName("uri");

            for (int i = 0; i < nodeList.getLength(); i++) {
                String uri = nodeList.item(i).getTextContent();
                ThingDescription td = getRobotTd(uri);

                // check if TD has the right title
                if (td.getTitle().equals(thingTitle)) {
                    LOGGER.info("Found Thing Description for robot " + thingTitle);
                    return td;
                }
            }
        } catch (IOException |
                 ParserConfigurationException |
                 InterruptedException |
                 SAXException e) {
            LOGGER.error("Error while searching for TD: " + e.getMessage());
        }
        throw new ThingDescriptionNotFoundException("No TD with title " + thingTitle + " found");
    }

    private ThingDescription getRobotTd(String robotUri) {
        try {
            return TDGraphReader.readFromURL(ThingDescription.TDFormat.RDF_TURTLE, robotUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
