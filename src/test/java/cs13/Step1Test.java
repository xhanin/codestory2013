package cs13;

import gumi.builders.UrlBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class Step1Test {

    @Test
    public void should_server_respond_email_when_question_asked() throws IOException {
        TextResource resource = new Resty().text(
                uriBuilder()
                        .addParameter("q", "Quelle est ton adresse email")
                        .toUri());
        assertThat(
                resource.toString(),
                is("xavier.hanin@gmail.com"));

        assertThat(resource.http().getHeaderField(Step1Handler.X_CODE_STORY_ERROR), nullValue());
    }


    @Test
    public void should_server_respond_email_with_error_header_when_no_question_asked() throws IOException {
        TextResource resource = new Resty().text(
                uriBuilder()
                        .toUri());
        assertThat(
                resource.toString(),
                is("xavier.hanin@gmail.com"));

        assertThat(resource.http().getHeaderField(Step1Handler.X_CODE_STORY_ERROR), is(Step1Handler.ErrorMessages.NO_QUESTION));
    }

    @Test
    public void should_server_respond_email_with_error_header_when_bad_question_asked() throws IOException {
        TextResource resource = new Resty().text(
                uriBuilder()
                        .addParameter("q", "Est-ce que vous jouez du violon ?")
                        .toUri());
        assertThat(
                resource.toString(),
                is("xavier.hanin@gmail.com"));

        assertThat(resource.http().getHeaderField(Step1Handler.X_CODE_STORY_ERROR), is(Step1Handler.ErrorMessages.BAD_QUESTION));
    }


    @Test
    public void should_server_respond_email_with_error_header_when_bad_path_provided() throws IOException {
        TextResource resource = new Resty().text(
                uriBuilder()
                        .withPath("/come-on")
                        .addParameter("q", "Quelle est ton adresse email")
                        .toUri());
        assertThat(
                resource.toString(),
                is("xavier.hanin@gmail.com"));

        assertThat(resource.http().getHeaderField(Step1Handler.X_CODE_STORY_ERROR), is(Step1Handler.ErrorMessages.BAD_PATH));
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

    private UrlBuilder uriBuilder() {
        return UrlBuilder.fromUri(codeStory2013.uri());
    }
}
