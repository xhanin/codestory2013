package cs13.handlers;

import cs13.scalaskel.ScalaskelDecomposer;
import cs13.scalaskel.ScalaskelDecomposition;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.util.Set;

public class ScalaskelDecomposerHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger("scalaskel");

    private ScalaskelDecomposer decomposer = new ScalaskelDecomposer();

    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl httpControl) throws Exception {
        int n = Integer.parseInt(request.uri().substring("/scalaskel/change/".length()));
        Set<ScalaskelDecomposition> decompositions = decomposer.decompose(n);

        String json = new ObjectMapper().writeValueAsString(decompositions);
        logger.info("{} => {}", n, json);
        response.content(json).end();
    }

}
