package cs13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.WebServer;
import org.webbitserver.handler.DateHeaderHandler;
import org.webbitserver.handler.ServerHeaderHandler;
import org.webbitserver.netty.NettyWebServer;
import webbit.LoggingHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public class CodeStory2013 {

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
                .add(new ScalaskelDecomposerHandler())
                .add(new MarkdownQuestionHandler())
                .add(new BasicQuestionHandler());
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
