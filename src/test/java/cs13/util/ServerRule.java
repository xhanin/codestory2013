package cs13.util;

import cs13.CodeStory2013;
import org.jboss.resteasy.client.ClientRequest;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutionException;

public class ServerRule implements TestRule {
    private CodeStory2013 codeStory2013;

    private void startServer() throws ExecutionException, InterruptedException, IOException {
        codeStory2013 = new CodeStory2013(findAvailablePort());
        codeStory2013.start();
    }

    private void stopServer() {
        codeStory2013.stop();
    }

    public ClientRequest request(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return new ClientRequest(codeStory2013.uri() + path);
    }

    private int findAvailablePort() throws IOException {
        try (ServerSocket s = new ServerSocket(0)) {
            return s.getLocalPort();
        }
    }


    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                startServer();
                try {
                    base.evaluate();
                } finally {
                    stopServer();
                }
            }
        };
    }
}
