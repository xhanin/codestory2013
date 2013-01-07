package webbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;
import org.webbitserver.wrapper.HttpResponseWrapper;

public class LoggingHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger("HTTP");

    public void handleHttpRequest(final HttpRequest request, final HttpResponse response, HttpControl control) throws Exception {
        logger.debug("REQUEST {}", request);
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response) {
            @Override
            public HttpResponseWrapper end() {
                logger.info("{} - {}", response.status(), request);
                return super.end();
            }

            @Override
            public HttpResponseWrapper error(Throwable error) {
                logger.error(request + ": " + error.getMessage(), error);
                return super.error(error);
            }
        };

        control.nextHandler(request, responseWrapper, control);
    }

}
