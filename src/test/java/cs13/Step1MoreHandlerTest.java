package cs13;

import org.junit.ClassRule;
import org.junit.Test;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Step1MoreHandlerTest {
    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void should_give_hint_that_there_is_more() throws IOException {
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .toUri());

        doGET(resource);

        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.MORE_HINT), notNullValue());
    }

    @Test
    public void should_give_more_answers() throws IOException {
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .addParameter("q", "give-twitter-name-github-and-birthdate")
                        .toUri());

        doGET(resource);

        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.MORE_HINT), nullValue());
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.NAME), is("Xavier Hanin"));
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.BIRTH_DATE), is("26/09/1976"));
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.TWITTER_ID), is("@xavierhanin"));
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.GITHUB), is("https://github.com/xhanin/"));
    }

    private String doGET(TextResource resource) {
        return resource.toString();
    }


}
