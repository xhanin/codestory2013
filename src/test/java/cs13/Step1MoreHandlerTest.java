package cs13;

import gumi.builders.UrlBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: xavierhanin
 * Date: 1/5/13
 * Time: 1:10 AM
 */
public class Step1MoreHandlerTest {

    @Test
    public void should_give_hint_that_there_is_more() throws IOException {
        TextResource resource = new Resty().text(
                uriBuilder()
                        .toUri());

        doGET(resource);

        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.MORE_HINT), notNullValue());
    }

    @Test
    public void should_give_more_answers() throws IOException {
        TextResource resource = new Resty().text(
                uriBuilder()
                        .addParameter("q", "give-twitter-name-github-and-birthdate")
                        .toUri());

        doGET(resource);

        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.MORE_HINT), nullValue());
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.NAME), is("Xavier Hanin"));
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.BIRTH_DATE), is("26/09/1976"));
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.TWITTER_ID), is("@xavierhanin"));
        assertThat(resource.http().getHeaderField(Step1MoreHandler.Headers.GITHUB), is("https://github.com/xhanin/"));
    }


    private static CodeStory2013 codeStory2013;

    @BeforeClass
    public static void startServer() throws ExecutionException, InterruptedException {
        codeStory2013 = new CodeStory2013(8087);
        codeStory2013.start();
    }

    @AfterClass
    public static void stopServer() {
        codeStory2013.stop();
    }


    private String doGET(TextResource resource) {
        return resource.toString();
    }

    private UrlBuilder uriBuilder() {
        return UrlBuilder.fromUri(codeStory2013.uri());
    }

}
