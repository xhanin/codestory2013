package cs13;

import cs13.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.WebServer;
import org.webbitserver.handler.DateHeaderHandler;
import org.webbitserver.handler.NotFoundHttpHandler;
import org.webbitserver.handler.ServerHeaderHandler;
import org.webbitserver.netty.NettyWebServer;
import webbit.LoggingHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public class CodeStory2013 {

    public static final int KILO = 1024;
    public static final int MEGA = 1024 * KILO;
    private final WebServer webServer;

    private final Logger logger = LoggerFactory.getLogger("MAIN");

    public CodeStory2013(int port) {
        webServer = new NettyWebServer(port) {
            @Override
            protected void setupDefaultHandlers() {
                add(new ServerHeaderHandler("GuessIt!"));
                add(new DateHeaderHandler());
                add(new LoggingHandler());
            }
        }
        .maxContentLength(20 * MEGA)
        .add("/jajascript/optimize", new JJRentalOptimizerHandler())
        .add("/scalaskel/change/.*", new ScalaskelDecomposerHandler())
        .add("/enonce/.*", new MarkdownQuestionHandler())
        .add("/", new BasicQuestionHandler())
        .add(new AnyPostHandler())
        .add(new NotFoundHttpHandler());
    }

    public void start() throws ExecutionException, InterruptedException {
        webServer.start().get();
        logger.info("Listening on " + webServer.getUri());
    }

    public URI uri() {
        return webServer.getUri();
    }
    public void stop() {
        webServer.stop();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new CodeStory2013(8086).start();
    }
}
