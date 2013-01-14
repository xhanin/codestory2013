package cs13.handlers;

import cs13.util.ServerRule;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Theories.class)
public class BasicQuestionHandlerTest {

    @ClassRule
    public static ServerRule server = new ServerRule();

    @DataPoints
    public static Question[] data() {
        return new Question[] {
            q("Quelle est ton adresse email", "xavier.hanin@gmail.com"),
            q("Es tu abonne a la mailing list(OUI/NON)", "OUI"),
            q("Es tu heureux de participer(OUI/NON)", "OUI"),
            q("Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)", "OUI"),
            q("As tu bien recu le premier enonce(OUI/NON)", "OUI"),
            q("Est ce que tu reponds toujours oui(OUI/NON)", "NON"),
            q("1+1", "2"),
            q("2+2", "4"),
            q("(1+2)/2", "1,5"),
            q("((1+2)+3+4+(5+6+7)+(8+9+10)*3)/2*5", "272,5"),
            q("1,5*4", "6"),
            q("((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*553344300034334349999000",
                    "31878018903828899277492024491376690701584023926880"),
            q("As tu passe une bonne nuit malgre les bugs de l etape precedente(PAS_TOP/BOF/QUELS_BUGS)", "PAS_TOP"),
            q("As tu bien recu le second enonce(OUI/NON)", "OUI"),
        };
    }

    @Test
    public void should_server_respond_400_when_no_question_asked() throws Exception {
        ClientResponse<String> response = server.request("/").get();
        assertThat(response.getResponseStatus().getStatusCode(), is(400));
    }

    @Test
    public void should_server_respond_404_when_bad_path_provided() throws Exception {
        ClientResponse<String> response = server.request("/baduri").get();
        assertThat(response.getResponseStatus().getStatusCode(), is(404));
    }

    @Theory
    public void should_server_respond_to_basic_question(Question q) throws Exception {
        ClientResponse<String> response = server.request("/").queryParameter("q", q.question).get(String.class);
        assertThat(
                "expected " + q.answer + " when asking " + q.question,
                response.getEntity(),
                is(q.answer));
    }

    private static Question q(String question, String answer) {
        return new Question(question, answer);
    }

    private static class Question {
        private String question;
        private String answer;

        private Question(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }
}
