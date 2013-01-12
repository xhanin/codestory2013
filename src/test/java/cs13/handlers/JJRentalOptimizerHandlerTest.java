package cs13.handlers;

import cs13.util.ServerRule;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class JJRentalOptimizerHandlerTest {

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void should_server_return_optimization_when_asked() throws Exception {
        ClientResponse<String> response = server.request("/jajascript/optimize")
                .body("application/json", ("[\n" +
                        "                { \"VOL\": \"MONAD42\", \"DEPART\": 0, \"DUREE\": 5, \"PRIX\": 10 },\n" +
                        "                { \"VOL\": \"META18\", \"DEPART\": 3, \"DUREE\": 7, \"PRIX\": 14 },\n" +
                        "                { \"VOL\": \"LEGACY01\", \"DEPART\": 5, \"DUREE\": 9, \"PRIX\": 8 },\n" +
                        "                { \"VOL\": \"YAGNI17\", \"DEPART\": 5, \"DUREE\": 9, \"PRIX\": 7 }\n" +
                        "]").getBytes("UTF-8")).post(String.class);

        assertThat(response.getResponseStatus().getStatusCode(), is(200));

        JsonNode json = new ObjectMapper().readTree(response.getEntity());
        assertThat(json, is(notNullValue()));
        assertThat(json.isObject(), is(true));

        assertThat(json.get("gain").asInt(), is(18));
        assertThat(json.get("path").isArray(), is(true));
        assertThat(json.get("path").size(), is(2));
        assertThat(json.get("path").get(0).asText(), is("MONAD42"));
        assertThat(json.get("path").get(1).asText(), is("LEGACY01"));
    }

}
