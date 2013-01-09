package cs13;

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

    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl httpControl) throws Exception {
        String q = request.queryParam("q");

        if (!"/".equals(URI.create(request.uri()).getPath())) {
            response.status(404).content(ErrorMessages.BAD_PATH).end();
            return;
        }

        if (q == null) {
            response.status(400).content(ErrorMessages.NO_QUESTION).end();
            return;
        }

        if ("Quelle est ton adresse email".equalsIgnoreCase(q)) {
            response.content("xavier.hanin@gmail.com").end();
        } else if ("Es tu abonne a la mailing list(OUI/NON)".equalsIgnoreCase(q)) {
            response.content("OUI").end();
        } else if ("Es tu heureux de participer(OUI/NON)".equalsIgnoreCase(q)) {
            response.content("OUI").end();
        } else {
            response.status(412).content(ErrorMessages.BAD_QUESTION).end();
        }

    }
}
