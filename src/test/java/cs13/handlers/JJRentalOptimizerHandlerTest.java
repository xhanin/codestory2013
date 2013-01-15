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

        assertThat(response.getResponseStatus().getStatusCode(), is(201));

        JsonNode json = new ObjectMapper().readTree(response.getEntity());
        assertThat(json, is(notNullValue()));
        assertThat(json.isObject(), is(true));

        assertThat(json.get("gain").asInt(), is(18));
        assertThat(json.get("path").isArray(), is(true));
        assertThat(json.get("path").size(), is(2));
        assertThat(json.get("path").get(0).asText(), is("MONAD42"));
        assertThat(json.get("path").get(1).asText(), is("LEGACY01"));
    }


    @Test
    public void should_server_return_optimization_when_asked_fine_purple_84() throws Exception {
        ClientResponse<String> response = server.request("/jajascript/optimize")
                .body("application/json",
                        ("[{\"VOL\":\"fine-purple-84\",\"DEPART\":0,\"DUREE\":4,\"PRIX\":6},{\"VOL\":\"tense-sorceress-94\",\"DEPART\":1,\"DUREE\":2,\"PRIX\":3},{\"VOL\":\"splendid-chickenpox-10\",\"DEPART\":2,\"DUREE\":6,\"PRIX\":2},{\"VOL\":\"important-stadium-43\",\"DEPART\":4,\"DUREE\":5,\"PRIX\":6},{\"VOL\":\"hilarious-leprechaun-30\",\"DEPART\":5,\"DUREE\":2,\"PRIX\":8},{\"VOL\":\"skinny-superman-4\",\"DEPART\":5,\"DUREE\":4,\"PRIX\":8},{\"VOL\":\"dizzy-footlocker-3\",\"DEPART\":6,\"DUREE\":2,\"PRIX\":7},{\"VOL\":\"crowded-zirconium-79\",\"DEPART\":7,\"DUREE\":6,\"PRIX\":7},{\"VOL\":\"frantic-paintbrush-22\",\"DEPART\":9,\"DUREE\":5,\"PRIX\":11},{\"VOL\":\"tough-graph-46\",\"DEPART\":10,\"DUREE\":2,\"PRIX\":23},{\"VOL\":\"slow-chair-82\",\"DEPART\":10,\"DUREE\":4,\"PRIX\":7},{\"VOL\":\"creepy-cougar-5\",\"DEPART\":11,\"DUREE\":2,\"PRIX\":1},{\"VOL\":\"precious-soil-4\",\"DEPART\":12,\"DUREE\":6,\"PRIX\":6},{\"VOL\":\"obedient-turpitude-48\",\"DEPART\":14,\"DUREE\":5,\"PRIX\":11},{\"VOL\":\"foolish-square-63\",\"DEPART\":15,\"DUREE\":2,\"PRIX\":18},{\"VOL\":\"tiny-crate-59\",\"DEPART\":15,\"DUREE\":4,\"PRIX\":14},{\"VOL\":\"foolish-sextet-54\",\"DEPART\":16,\"DUREE\":2,\"PRIX\":3},{\"VOL\":\"cute-reactivation-59\",\"DEPART\":17,\"DUREE\":6,\"PRIX\":7},{\"VOL\":\"deafening-harpoon-1\",\"DEPART\":19,\"DUREE\":5,\"PRIX\":20},{\"VOL\":\"excited-sport-32\",\"DEPART\":20,\"DUREE\":2,\"PRIX\":10},{\"VOL\":\"graceful-ballistics-11\",\"DEPART\":20,\"DUREE\":4,\"PRIX\":7},{\"VOL\":\"envious-loganberry-22\",\"DEPART\":21,\"DUREE\":2,\"PRIX\":2},{\"VOL\":\"miniature-buddy-7\",\"DEPART\":22,\"DUREE\":6,\"PRIX\":6},{\"VOL\":\"healthy-violet-90\",\"DEPART\":24,\"DUREE\":5,\"PRIX\":22},{\"VOL\":\"proud-radiology-64\",\"DEPART\":25,\"DUREE\":2,\"PRIX\":20},{\"VOL\":\"nasty-jellybean-84\",\"DEPART\":25,\"DUREE\":4,\"PRIX\":10},{\"VOL\":\"courageous-sunbonnet-55\",\"DEPART\":26,\"DUREE\":2,\"PRIX\":2},{\"VOL\":\"embarrassed-grease-69\",\"DEPART\":27,\"DUREE\":6,\"PRIX\":5},{\"VOL\":\"cooperative-ewe-74\",\"DEPART\":29,\"DUREE\":5,\"PRIX\":6},{\"VOL\":\"grotesque-runway-53\",\"DEPART\":30,\"DUREE\":2,\"PRIX\":5},{\"VOL\":\"tall-sunbather-33\",\"DEPART\":30,\"DUREE\":4,\"PRIX\":15},{\"VOL\":\"open-hiker-13\",\"DEPART\":31,\"DUREE\":2,\"PRIX\":10},{\"VOL\":\"ancient-workaholic-10\",\"DEPART\":32,\"DUREE\":6,\"PRIX\":2},{\"VOL\":\"blue-eyed-slob-17\",\"DEPART\":34,\"DUREE\":5,\"PRIX\":15},{\"VOL\":\"frail-nut-53\",\"DEPART\":35,\"DUREE\":2,\"PRIX\":21},{\"VOL\":\"long-uturn-85\",\"DEPART\":35,\"DUREE\":4,\"PRIX\":6},{\"VOL\":\"shrill-graveyard-21\",\"DEPART\":36,\"DUREE\":2,\"PRIX\":10},{\"VOL\":\"elated-forest-90\",\"DEPART\":37,\"DUREE\":6,\"PRIX\":7},{\"VOL\":\"funny-vinyl-68\",\"DEPART\":39,\"DUREE\":5,\"PRIX\":20},{\"VOL\":\"faithful-banker-80\",\"DEPART\":40,\"DUREE\":2,\"PRIX\":23},{\"VOL\":\"relieved-revolt-99\",\"DEPART\":40,\"DUREE\":4,\"PRIX\":12},{\"VOL\":\"chubby-level-37\",\"DEPART\":41,\"DUREE\":2,\"PRIX\":6},{\"VOL\":\"poor-confetti-26\",\"DEPART\":42,\"DUREE\":6,\"PRIX\":6},{\"VOL\":\"amused-musketeer-65\",\"DEPART\":44,\"DUREE\":5,\"PRIX\":23},{\"VOL\":\"old-bean-85\",\"DEPART\":45,\"DUREE\":2,\"PRIX\":18},{\"VOL\":\"purring-color-61\",\"DEPART\":45,\"DUREE\":4,\"PRIX\":13},{\"VOL\":\"tender-turquoise-60\",\"DEPART\":46,\"DUREE\":2,\"PRIX\":2},{\"VOL\":\"nutty-hazelnut-41\",\"DEPART\":47,\"DUREE\":6,\"PRIX\":1},{\"VOL\":\"mammoth-wildlife-32\",\"DEPART\":49,\"DUREE\":5,\"PRIX\":7},{\"VOL\":\"difficult-lizard-12\",\"DEPART\":50,\"DUREE\":2,\"PRIX\":23},{\"VOL\":\"raspy-slime-79\",\"DEPART\":50,\"DUREE\":4,\"PRIX\":6},{\"VOL\":\"glamorous-glycerine-63\",\"DEPART\":51,\"DUREE\":2,\"PRIX\":3},{\"VOL\":\"narrow-hotel-63\",\"DEPART\":52,\"DUREE\":6,\"PRIX\":1},{\"VOL\":\"encouraging-cupboard-7\",\"DEPART\":54,\"DUREE\":5,\"PRIX\":5},{\"VOL\":\"careful-hatchback-74\",\"DEPART\":55,\"DUREE\":2,\"PRIX\":21}]").getBytes("UTF-8")).post(String.class);

        assertThat(response.getResponseStatus().getStatusCode(), is(201));

        JsonNode json = new ObjectMapper().readTree(response.getEntity());
        assertThat(json, is(notNullValue()));
        assertThat(json.isObject(), is(true));

        // {"gain":223,"path":["fine-purple-84","hilarious-leprechaun-30","tough-graph-46","foolish-square-63","deafening-harpoon-1","healthy-violet-90","tall-sunbather-33","frail-nut-53","faithful-banker-80","amused-musketeer-65","difficult-lizard-12","careful-hatchback-74"]}
        assertThat(json.get("gain").asInt(), is(223));
        assertThat(json.get("path").isArray(), is(true));
        assertThat(json.get("path").size(), is(12));
        assertThat(json.get("path").get(0).asText(), is("fine-purple-84"));
        // assertThat(json.get("path").get(1).asText(), is("hilarious-leprechaun-30")); // this is the other option, current algo find other one
        assertThat(json.get("path").get(1).asText(), is("skinny-superman-4"));
        assertThat(json.get("path").get(11).asText(), is("careful-hatchback-74"));
    }
}
