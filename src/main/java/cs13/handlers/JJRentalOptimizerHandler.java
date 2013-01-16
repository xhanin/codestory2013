package cs13.handlers;

import cs13.jjrental.JJOptimization;
import cs13.jjrental.JJRentalOptimizer;
import cs13.jjrental.TripOrder;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.util.List;

/**
 * User: xavierhanin
 * Date: 1/12/13
 * Time: 2:38 PM
 */
public class JJRentalOptimizerHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger("JJRENTAL");

    private JJRentalOptimizer optimizer = new JJRentalOptimizer();

    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl httpControl) throws Exception {
        if (!"POST".equals(request.method())) {
            httpControl.nextHandler(request, response, httpControl);
            return;
        }

        String q = request.body();

        try {
            List<TripOrder> orders = new ObjectMapper().readValue(q, new TypeReference<List<TripOrder>>() { });

            long start = System.currentTimeMillis();
            JJOptimization optimization = optimizer.optimize(orders);
            logger.info("optimized {} orders in {} ms", orders.size(), System.currentTimeMillis() - start);

            String json = new ObjectMapper().writeValueAsString(optimization);
            logger.info("{} => {}", q, json);
            response.status(201).header("Content-Type", "application/json").content(json).end();
        } catch (JsonParseException e) {
            response.status(400).content("malformed json: " + e.getMessage()).end();
        } catch (JsonMappingException e) {
            response.status(400).content("invalid json: " + e.getMessage()).end();
        }

    }
}
