package cs13;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.net.URI;

public class Step1Handler implements HttpHandler {
    public static final String X_CODE_STORY_ERROR = "X-CodeStory-Error";

    public static final class ErrorMessages {
        public static final String NO_QUESTION = "Vous avez pas oublie la question ? Bon aller j'y reponds quand meme...";
        public static final String BAD_QUESTION = "Pas vraiment la question a laquelle je m'attendais, je vous donne quand meme mon email on sait jamais";
        public static final String BAD_PATH = "Je croyais qu'on devait repondre sur / seulement, bon aller pour cette fois je vous donne quand meme mon email";
    }

    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl httpControl) throws Exception {
        String q = request.queryParam("q");
        if (q == null) {
            addErrorHeader(response, ErrorMessages.NO_QUESTION);
        } else if (!"Quelle est ton adresse email".equalsIgnoreCase(q)) {
            addErrorHeader(response, ErrorMessages.BAD_QUESTION);
        }

        if (!"/".equals(URI.create(request.uri()).getPath())) {
            addErrorHeader(response, ErrorMessages.BAD_PATH);
        }

        response.content("xavier.hanin@gmail.com").end();
    }

    private HttpResponse addErrorHeader(HttpResponse response, String message) {
        return response.header(X_CODE_STORY_ERROR, message);
    }
}
