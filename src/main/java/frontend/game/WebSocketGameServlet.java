package frontend.game;


import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by stalker on 29.01.16.
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/gameplay"})
public class WebSocketGameServlet extends WebSocketServlet {

    @Override
    public void configure(WebSocketServletFactory factory){
        factory.getPolicy().setIdleTimeout(60000);
        factory.setCreator(new GameWebSocketCreator());
    }

}
