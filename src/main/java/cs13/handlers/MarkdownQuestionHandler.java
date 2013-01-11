package cs13.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

public class MarkdownQuestionHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger("QUESTION");

    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl httpControl) throws Exception {
        if (!"POST".equals(request.method())) {
            httpControl.nextHandler(request, response, httpControl);
            return;
        }

        String q = request.body();

        logger.info("{} : {}", 201, q);
        response.status(201).content("OK").end();
    }

}
