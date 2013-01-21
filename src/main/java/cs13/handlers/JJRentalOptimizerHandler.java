package cs13.handlers;

import com.google.common.io.CharStreams;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static cs13.handlers.WebbitHelper.getBodyAsStream;

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
        logger.info("optimization request received... content length is {}", request.header("Content-Length"));

        InputStream qStream = getBodyAsStream(request);
        logger.info("optimization request streamed...");
        String qJson = CharStreams.toString(new InputStreamReader(qStream));
        logger.info("optimization request stringed, length is {}", qJson.length());

        try {
            List<TripOrder> orders = new ObjectMapper().readValue(qJson, new TypeReference<List<TripOrder>>() { });
            logger.info("optimization request mapped, nb orders is {}", orders.size());

            long start = System.currentTimeMillis();
            JJOptimization optimization = optimizer.optimize(orders);
            logger.info("optimized {} orders in {} ms", orders.size(), System.currentTimeMillis() - start);

            String json = new ObjectMapper().writeValueAsString(optimization);
            logger.info("{} => {}", orders.size() > 100 ? orders.subList(0, 100) + " [...]" : orders, json);
            response.status(201).header("Content-Type", "application/json").content(json).end();
        } catch (JsonParseException e) {
            logger.info("malformed json: {} <= {} chars\n{}", new Object[] {e.getMessage(), qJson.length(), qJson});
            response.status(400).content("malformed json: " + e.getMessage()).end();
        } catch (JsonMappingException e) {
            logger.info("invalid json: {} <=\n{}", e.getMessage(), qJson);
            response.status(400).content("invalid json: " + e.getMessage()).end();
        }

    }
}
