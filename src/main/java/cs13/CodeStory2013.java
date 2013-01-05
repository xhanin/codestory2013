package cs13;

import org.webbitserver.WebServer;
import org.webbitserver.handler.DateHeaderHandler;
import org.webbitserver.handler.ServerHeaderHandler;
import org.webbitserver.netty.NettyWebServer;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public class CodeStory2013 {

    private final WebServer webServer;

    public CodeStory2013(int port) {
        webServer = new NettyWebServer(port) {
            @Override
            protected void setupDefaultHandlers() {
                add(new ServerHeaderHandler("GuessIt!"));
                add(new DateHeaderHandler());
            }
        }
                .add(new Step1MoreHandler())
                .add(new Step1Handler());
    }

    public void start() throws ExecutionException, InterruptedException {
        webServer.start().get();
        System.out.println("Listening on " + webServer.getUri());
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
