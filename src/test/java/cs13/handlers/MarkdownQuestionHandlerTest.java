package cs13.handlers;

import cs13.util.LogbackCapturingAppender;
import cs13.util.ServerRule;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.LoggerFactory;

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
    public void should_server_store_question_and_return_201_when_markdown_question_asked() throws Exception {
        LogbackCapturingAppender capturing = LogbackCapturingAppender.Factory
                .weaveInto(LoggerFactory.getLogger("QUESTION"));
        ClientResponse response = server.request("/enonce/1")
                .body("text/x-markdown", A_QUESTION_EXAMPLE.getBytes("UTF-8")).post();

        assertThat(response.getResponseStatus().getStatusCode(), is(201));
        assertThat(
                capturing.getCapturedLogMessage(),
                containsString(A_QUESTION_EXAMPLE));
    }

}
