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
    public void should_server_respond_email_when_question_asked() throws IOException {
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .addParameter("q", "Quelle est ton adresse email")
                        .toUri());
        assertThat(
                resource.toString(),
                is("xavier.hanin@gmail.com"));
    }

    @Test
    public void should_server_respond_yes_when_mailing_list_subscription_asked() throws IOException {
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .addParameter("q", "Es tu abonne a la mailing list(OUI/NON)")
                        .toUri());
        assertThat(
                resource.toString(),
                is("OUI"));
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


}
