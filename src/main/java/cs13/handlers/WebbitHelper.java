package cs13.handlers;

import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.webbitserver.HttpRequest;
import org.webbitserver.netty.NettyHttpRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * User: xavierhanin
 * Date: 1/21/13
 * Time: 11:21 PM
 */
public class WebbitHelper {
    static InputStream getBodyAsStream(HttpRequest request) {
        if (request instanceof NettyHttpRequest) {
            try {
                Field field = NettyHttpRequest.class.getDeclaredField("httpRequest");
                field.setAccessible(true);
                org.jboss.netty.handler.codec.http.HttpRequest nettyRequest
                        = (org.jboss.netty.handler.codec.http.HttpRequest) field.get(request);

                return new ChannelBufferInputStream(nettyRequest.getContent());
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ByteArrayInputStream(request.bodyAsBytes());
        }
    }
}
