package cs13.handlers;

import cs13.util.ServerRule;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicQuestionHandlerTest {

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void should_server_respond_to_basic_questions() throws Exception {
        askBasicQuestionAndExpectAnswer("Quelle est ton adresse email", "xavier.hanin@gmail.com");
        askBasicQuestionAndExpectAnswer("Es tu abonne a la mailing list(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("Es tu heureux de participer(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("As tu bien recu le premier enonce(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("Est ce que tu reponds toujours oui(OUI/NON)", "NON");
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

    private void askBasicQuestionAndExpectAnswer(String question, String answer) throws Exception {
        ClientResponse<String> response = server.request("/").queryParameter("q", question).get(String.class);
        assertThat(
                "expected " + answer + " when asking " + question,
                response.getEntity(),
                is(answer));
    }

}
