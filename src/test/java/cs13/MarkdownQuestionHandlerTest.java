package cs13;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import us.monoid.web.Content;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MarkdownQuestionHandlerTest {

    public static final String A_QUESTION_EXAMPLE = "A question example";
    @ClassRule
    public static ServerRule server = new ServerRule();

    @After
    public void cleanUp() {
        LogbackCapturingAppender.Factory.cleanUp();
    }

    @Test
    public void should_server_store_question_and_return_201_when_markdown_question_asked() throws IOException {
        LogbackCapturingAppender capturing = LogbackCapturingAppender.Factory
                .weaveInto(LoggerFactory.getLogger("QUESTION"));
        TextResource resource = new Resty().text(
                server.uriBuilder()
                        .withPath("/enonce/1")
                        .toUri(),
                new Content("text/x-markdown", A_QUESTION_EXAMPLE.getBytes("UTF-8")));

        assertThat(resource.status(201), is(true));
        assertThat(
                capturing.getCapturedLogMessage(),
                containsString(A_QUESTION_EXAMPLE));
    }

}
