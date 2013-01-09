package cs13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.net.URI;

public class BasicQuestionHandler implements HttpHandler {
    public static final class ErrorMessages {
        public static final String NO_QUESTION = "Vous avez pas oublie la question ? Pas moyen d'y repondre...";
        public static final String BAD_QUESTION = "Pas vraiment la question a laquelle je m'attendais, mais je vais y travailler";
        public static final String BAD_PATH = "Je croyais qu'on devait repondre sur / seulement, bon aller pour cette fois je vous donne quand meme mon email";
    }

    private final Logger logger = LoggerFactory.getLogger("QUESTION");

    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl httpControl) throws Exception {

        if (!"/".equals(URI.create(request.uri()).getPath())) {
            respondError(response, 404, ErrorMessages.BAD_PATH);
            return;
        }

        String q = request.queryParam("q");
        if (q == null) {
            respondError(response, 400, ErrorMessages.NO_QUESTION);
            return;
        }

        if ("Quelle est ton adresse email".equalsIgnoreCase(q)) {
            respond(response, q, "xavier.hanin@gmail.com");
        } else if ("Es tu abonne a la mailing list(OUI/NON)".equalsIgnoreCase(q)) {
            respond(response, q, "OUI");
        } else if ("Es tu heureux de participer(OUI/NON)".equalsIgnoreCase(q)) {
            respond(response, q, "OUI");
        } else {
            respondError(response, 412, ErrorMessages.BAD_QUESTION);
        }

    }

    private HttpResponse respondError(HttpResponse response, int status, String r) {
        logger.info("{} : {}", status, r);
        return response.status(status).content(r).end();
    }

    private HttpResponse respond(HttpResponse response, String q, String r) {
        logger.info("{} => {}", q, r);
        return response.content(r).end();
    }
}
