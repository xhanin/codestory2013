package cs13;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.util.Locale;

public class Step1MoreHandler implements HttpHandler {


    public static final class Headers {
        public static final String MORE_HINT = "X-CodeStory-More";
        public static final String NAME = "X-CodeStory-Name";
        public static final String BIRTH_DATE = "X-CodeStory-BirthDate";
        public static final String TWITTER_ID = "X-CodeStory-TwitterId";
        public static final String GITHUB = "X-CodeStory-GitHub";
    }

    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        boolean addMore = false;
        String uri = request.uri().toLowerCase(Locale.FRENCH);
        if (uri.contains("twitter")) {
            response.header(Headers.TWITTER_ID, "@xavierhanin");
            addMore = true;
        }
        if (uri.contains("nom") || uri.contains("name")) {
            response.header(Headers.NAME, "Xavier Hanin");
            addMore = true;
        }
        if (uri.contains("github")) {
            response.header(Headers.GITHUB, "https://github.com/xhanin/");
            addMore = true;
        }
        if (uri.contains("datedenaissance") || uri.contains("birthdate")) {
            response.header(Headers.BIRTH_DATE, "26/09/1976");
            addMore = true;
        }

        if (!addMore) {
            response.header(Headers.MORE_HINT,
                    "je peux vous en dire un peu plus, essayez un peu de demander pour voir ;-)");
        }

        control.nextHandler(request, response, control);
    }
}
