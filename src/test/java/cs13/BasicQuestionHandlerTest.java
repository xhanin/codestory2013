package cs13;

import org.junit.ClassRule;
import org.junit.Test;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicQuestionHandlerTest {

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void should_server_respond_to_basic_questions() throws IOException {
        askBasicQuestionAndExpectAnswer("Quelle est ton adresse email", "xavier.hanin@gmail.com");
        askBasicQuestionAndExpectAnswer("Es tu abonne a la mailing list(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("Es tu heureux de participer(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("As tu bien recu le premier enonce(OUI/NON)", "OUI");
        askBasicQuestionAndExpectAnswer("Est ce que tu reponds toujours oui(OUI/NON)", "NON");
    }

    @Test(expected = IOException.class)
    public void should_server_respond_400_when_no_question_asked() throws IOException {
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .toUri());
    }

    @Test(expected = FileNotFoundException.class)
    public void should_server_respond_404_when_bad_path_provided() throws IOException {
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .withPath("/come-on")
                        .addParameter("q", "Quelle est ton adresse email")
                        .toUri());
    }

    private void askBasicQuestionAndExpectAnswer(String question, String response) throws IOException {
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .addParameter("q", question)
                        .toUri());
        assertThat(
                "expected " + response + " when asking " + question,
                resource.toString(),
                is(response));
    }

}
