package ch.unisg.executormiro;
import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.clients.TDCoapRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDCoapResponse;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExecutorMiroQueryService {

    public String querySensor(ThingDescription td, String endpoint) {
        try {

            Optional<PropertyAffordance> humidity = td.getPropertyByName(endpoint);

            if (humidity.isPresent()) {
                Optional<Form> form = humidity.get().getFirstFormForOperationType(TD.readProperty);

                if(form.isPresent()) {
                    TDCoapRequest request = new TDCoapRequest(form.get(), TD.readProperty);
                    TDCoapResponse response = request.execute();

                    if (response.getPayload().isPresent()) {
                        // got payload return value
                        ResponsePayload responsePayload = new Gson().fromJson(response.getPayload().get(), ResponsePayload.class);
                        return responsePayload.value.toString();
                    }
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}

class ResponsePayload {
    Float value;
}