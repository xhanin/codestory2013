package cs13.handlers;

import cs13.util.ServerRule;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Random;

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
        assertThat(json.get("path").get(1).asText(), is("hilarious-leprechaun-30"));
        // assertThat(json.get("path").get(1).asText(), is("skinny-superman-4")); // this is the other option, current algo find other one
        assertThat(json.get("path").get(11).asText(), is("careful-hatchback-74"));
    }

    @Test
    public void should_server_return_optimization_when_asked_whispering_putter_25() throws Exception {
        should_server_return_optimization_when_asked(
                "[{\"VOL\":\"whispering-putter-25\",\"DEPART\":0,\"DUREE\":4,\"PRIX\":8},{\"VOL\":\"hungry-hag-6\",\"DEPART\":1,\"DUREE\":2,\"PRIX\":4},{\"VOL\":\"beautiful-vocabulary-10\",\"DEPART\":2,\"DUREE\":6,\"PRIX\":7},{\"VOL\":\"courageous-light-68\",\"DEPART\":4,\"DUREE\":5,\"PRIX\":11},{\"VOL\":\"harsh-ant-9\",\"DEPART\":5,\"DUREE\":2,\"PRIX\":1},{\"VOL\":\"gifted-possum-86\",\"DEPART\":5,\"DUREE\":4,\"PRIX\":15},{\"VOL\":\"creepy-den-66\",\"DEPART\":6,\"DUREE\":2,\"PRIX\":4},{\"VOL\":\"husky-grater-12\",\"DEPART\":7,\"DUREE\":6,\"PRIX\":4},{\"VOL\":\"misty-macaroon-90\",\"DEPART\":9,\"DUREE\":5,\"PRIX\":16},{\"VOL\":\"hurt-lion-93\",\"DEPART\":10,\"DUREE\":2,\"PRIX\":12},{\"VOL\":\"helpful-panther-70\",\"DEPART\":10,\"DUREE\":4,\"PRIX\":15},{\"VOL\":\"helpful-matt-10\",\"DEPART\":11,\"DUREE\":2,\"PRIX\":6},{\"VOL\":\"slow-bath-19\",\"DEPART\":12,\"DUREE\":6,\"PRIX\":1},{\"VOL\":\"enthusiastic-alfalfa-44\",\"DEPART\":14,\"DUREE\":5,\"PRIX\":23},{\"VOL\":\"disturbed-springtime-72\",\"DEPART\":15,\"DUREE\":2,\"PRIX\":17},{\"VOL\":\"great-slyness-7\",\"DEPART\":15,\"DUREE\":4,\"PRIX\":12},{\"VOL\":\"nutty-goatee-75\",\"DEPART\":16,\"DUREE\":2,\"PRIX\":7},{\"VOL\":\"successful-tracer-37\",\"DEPART\":17,\"DUREE\":6,\"PRIX\":1},{\"VOL\":\"husky-tribune-83\",\"DEPART\":19,\"DUREE\":5,\"PRIX\":10},{\"VOL\":\"deep-music-85\",\"DEPART\":20,\"DUREE\":2,\"PRIX\":13},{\"VOL\":\"resonant-scrap-81\",\"DEPART\":20,\"DUREE\":4,\"PRIX\":9},{\"VOL\":\"lively-raise-63\",\"DEPART\":21,\"DUREE\":2,\"PRIX\":1},{\"VOL\":\"alive-ice-34\",\"DEPART\":22,\"DUREE\":6,\"PRIX\":5},{\"VOL\":\"amused-scumbag-75\",\"DEPART\":24,\"DUREE\":5,\"PRIX\":13},{\"VOL\":\"clumsy-surgery-73\",\"DEPART\":25,\"DUREE\":2,\"PRIX\":7},{\"VOL\":\"precious-beaker-18\",\"DEPART\":25,\"DUREE\":4,\"PRIX\":8},{\"VOL\":\"dark-beast-80\",\"DEPART\":26,\"DUREE\":2,\"PRIX\":8},{\"VOL\":\"enchanting-ventilation-28\",\"DEPART\":27,\"DUREE\":6,\"PRIX\":3},{\"VOL\":\"grotesque-polyester-76\",\"DEPART\":29,\"DUREE\":5,\"PRIX\":11},{\"VOL\":\"gentle-eyeglass-86\",\"DEPART\":30,\"DUREE\":2,\"PRIX\":3},{\"VOL\":\"famous-cake-97\",\"DEPART\":30,\"DUREE\":4,\"PRIX\":11},{\"VOL\":\"time-desk-37\",\"DEPART\":31,\"DUREE\":2,\"PRIX\":2},{\"VOL\":\"frightened-scalp-70\",\"DEPART\":32,\"DUREE\":6,\"PRIX\":3},{\"VOL\":\"powerful-knight-25\",\"DEPART\":34,\"DUREE\":5,\"PRIX\":10},{\"VOL\":\"flipped-out-ballet-94\",\"DEPART\":35,\"DUREE\":2,\"PRIX\":11},{\"VOL\":\"condemned-duct-57\",\"DEPART\":35,\"DUREE\":4,\"PRIX\":8},{\"VOL\":\"slow-suit-66\",\"DEPART\":36,\"DUREE\":2,\"PRIX\":9},{\"VOL\":\"whispering-prize-92\",\"DEPART\":37,\"DUREE\":6,\"PRIX\":1},{\"VOL\":\"silent-scholar-41\",\"DEPART\":39,\"DUREE\":5,\"PRIX\":12},{\"VOL\":\"worried-pediatric-74\",\"DEPART\":40,\"DUREE\":2,\"PRIX\":6}]"
            , 122);
    }

    @Test
    public void should_server_return_optimization_when_asked_gleaming_molar_86() throws Exception {
        should_server_return_optimization_when_asked(
                "\t[{\"VOL\":\"gleaming-molar-86\",\"DEPART\":0,\"DUREE\":4,\"PRIX\":11},{\"VOL\":\"super-storefront-97\",\"DEPART\":1,\"DUREE\":2,\"PRIX\":10},{\"VOL\":\"nervous-walkway-45\",\"DEPART\":2,\"DUREE\":6,\"PRIX\":1},{\"VOL\":\"obnoxious-mushroom-21\",\"DEPART\":4,\"DUREE\":5,\"PRIX\":13},{\"VOL\":\"upset-butterfly-44\",\"DEPART\":5,\"DUREE\":2,\"PRIX\":8},{\"VOL\":\"grumpy-voyager-1\",\"DEPART\":5,\"DUREE\":4,\"PRIX\":10},{\"VOL\":\"innocent-ballerina-10\",\"DEPART\":6,\"DUREE\":2,\"PRIX\":3},{\"VOL\":\"flat-wisdom-53\",\"DEPART\":7,\"DUREE\":6,\"PRIX\":6},{\"VOL\":\"black-bugle-48\",\"DEPART\":9,\"DUREE\":5,\"PRIX\":14},{\"VOL\":\"smiling-senselessness-26\",\"DEPART\":10,\"DUREE\":2,\"PRIX\":2},{\"VOL\":\"combative-custard-10\",\"DEPART\":10,\"DUREE\":4,\"PRIX\":14},{\"VOL\":\"resonant-herb-45\",\"DEPART\":11,\"DUREE\":2,\"PRIX\":2},{\"VOL\":\"tense-pilgrim-83\",\"DEPART\":12,\"DUREE\":6,\"PRIX\":7},{\"VOL\":\"zealous-wasp-12\",\"DEPART\":14,\"DUREE\":5,\"PRIX\":6},{\"VOL\":\"tiny-mathematics-52\",\"DEPART\":15,\"DUREE\":2,\"PRIX\":19},{\"VOL\":\"beautiful-sportswoman-85\",\"DEPART\":15,\"DUREE\":4,\"PRIX\":11},{\"VOL\":\"smiling-cerebellum-51\",\"DEPART\":16,\"DUREE\":2,\"PRIX\":2},{\"VOL\":\"troubled-goalie-66\",\"DEPART\":17,\"DUREE\":6,\"PRIX\":6},{\"VOL\":\"teeny-tiny-gourmet-21\",\"DEPART\":19,\"DUREE\":5,\"PRIX\":16},{\"VOL\":\"average-cursor-88\",\"DEPART\":20,\"DUREE\":2,\"PRIX\":4},{\"VOL\":\"bewildered-snap-5\",\"DEPART\":20,\"DUREE\":4,\"PRIX\":8},{\"VOL\":\"fantastic-rule-65\",\"DEPART\":21,\"DUREE\":2,\"PRIX\":3},{\"VOL\":\"fancy-advisor-19\",\"DEPART\":22,\"DUREE\":6,\"PRIX\":5},{\"VOL\":\"squealing-myth-86\",\"DEPART\":24,\"DUREE\":5,\"PRIX\":22},{\"VOL\":\"great-sphere-59\",\"DEPART\":25,\"DUREE\":2,\"PRIX\":17},{\"VOL\":\"vivacious-dealer-14\",\"DEPART\":25,\"DUREE\":4,\"PRIX\":6},{\"VOL\":\"tall-winter-70\",\"DEPART\":26,\"DUREE\":2,\"PRIX\":8},{\"VOL\":\"brave-gas-43\",\"DEPART\":27,\"DUREE\":6,\"PRIX\":1},{\"VOL\":\"sore-speckle-65\",\"DEPART\":29,\"DUREE\":5,\"PRIX\":5},{\"VOL\":\"gorgeous-klutz-96\",\"DEPART\":30,\"DUREE\":2,\"PRIX\":22}]"
            , 117);
    }

    @Test
    public void should_server_return_optimization_when_asked_shallow_kidnapper_93() throws Exception {
        should_server_return_optimization_when_asked(
                "\t[{\"VOL\":\"shallow-kidnapper-93\",\"DEPART\":0,\"DUREE\":4,\"PRIX\":10},{\"VOL\":\"easy-tableware-84\",\"DEPART\":1,\"DUREE\":2,\"PRIX\":7},{\"VOL\":\"clean-vista-85\",\"DEPART\":2,\"DUREE\":6,\"PRIX\":6},{\"VOL\":\"high-termite-66\",\"DEPART\":4,\"DUREE\":5,\"PRIX\":21},{\"VOL\":\"fragile-notch-18\",\"DEPART\":5,\"DUREE\":2,\"PRIX\":27},{\"VOL\":\"quiet-blackjack-43\",\"DEPART\":5,\"DUREE\":4,\"PRIX\":13},{\"VOL\":\"disturbed-cloak-27\",\"DEPART\":6,\"DUREE\":2,\"PRIX\":5},{\"VOL\":\"rich-bassist-15\",\"DEPART\":7,\"DUREE\":6,\"PRIX\":5},{\"VOL\":\"defeated-ripple-6\",\"DEPART\":9,\"DUREE\":5,\"PRIX\":7},{\"VOL\":\"zealous-wardroom-14\",\"DEPART\":10,\"DUREE\":2,\"PRIX\":14},{\"VOL\":\"great-tango-28\",\"DEPART\":10,\"DUREE\":4,\"PRIX\":13},{\"VOL\":\"wild-skunk-99\",\"DEPART\":11,\"DUREE\":2,\"PRIX\":7},{\"VOL\":\"blue-eyed-varnish-11\",\"DEPART\":12,\"DUREE\":6,\"PRIX\":6},{\"VOL\":\"poised-spareribs-13\",\"DEPART\":14,\"DUREE\":5,\"PRIX\":4},{\"VOL\":\"annoying-numismatist-69\",\"DEPART\":15,\"DUREE\":2,\"PRIX\":9},{\"VOL\":\"excited-popcorn-90\",\"DEPART\":15,\"DUREE\":4,\"PRIX\":14},{\"VOL\":\"quiet-actress-30\",\"DEPART\":16,\"DUREE\":2,\"PRIX\":5},{\"VOL\":\"thoughtless-floor-31\",\"DEPART\":17,\"DUREE\":6,\"PRIX\":2},{\"VOL\":\"bad-skunk-19\",\"DEPART\":19,\"DUREE\":5,\"PRIX\":7},{\"VOL\":\"odd-stowaway-80\",\"DEPART\":20,\"DUREE\":2,\"PRIX\":30},{\"VOL\":\"better-grain-97\",\"DEPART\":20,\"DUREE\":4,\"PRIX\":11},{\"VOL\":\"encouraging-meadowlark-20\",\"DEPART\":21,\"DUREE\":2,\"PRIX\":8},{\"VOL\":\"cheerful-violoncello-32\",\"DEPART\":22,\"DUREE\":6,\"PRIX\":2},{\"VOL\":\"fancy-throat-62\",\"DEPART\":24,\"DUREE\":5,\"PRIX\":14},{\"VOL\":\"splendid-sawdust-43\",\"DEPART\":25,\"DUREE\":2,\"PRIX\":13}]"
            , 109);
    }

    @Test
    public void should_server_return_optimization_when_asked_50000_orders() throws Exception {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i<50000; i++) {
            sb.append("{\"VOL\":\"shallow-kidnapper-"+i+"\",\"DEPART\":" + r.nextInt(24)+ ",\"DUREE\":" + r.nextInt(10) + ",\"PRIX\":" + r.nextInt(15) + "},");
        }
        sb.append("{\"VOL\":\"shallow-kidnapper-999999\",\"DEPART\":0,\"DUREE\":4,\"PRIX\":10}");
        sb.append("]");

        ClientResponse<String> response = server.request("/jajascript/optimize")
                .body("application/json", sb.toString().getBytes("UTF-8")).post(String.class);

        assertThat(response.getResponseStatus().getStatusCode(), is(201));
    }

    public void should_server_return_optimization_when_asked(String request, int expectedGain) throws Exception {
        ClientResponse<String> response = server.request("/jajascript/optimize")
                .body("application/json", request.getBytes("UTF-8")).post(String.class);

        assertThat(response.getResponseStatus().getStatusCode(), is(201));

        JsonNode json = new ObjectMapper().readTree(response.getEntity());
        assertThat(json, is(notNullValue()));
        assertThat(json.isObject(), is(true));

        assertThat(json.get("gain").asInt(), is(expectedGain));
    }


}
