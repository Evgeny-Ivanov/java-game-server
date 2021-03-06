package frontend.game;


import base.GameMechanics;
import base.WebSocketService;
import databaseService.AccountService;
import databaseService.DBServiceInMemory;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by stalker on 29.01.16.
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/gameplay"})
public class WebSocketGameServlet extends WebSocketServlet {

    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;
    private static final int TIMEOUT = 60000000;

    public WebSocketGameServlet(AccountService accountService, GameMechanics gameMechanics, WebSocketService webSocketService){
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }
    @Override
    public void configure(WebSocketServletFactory factory){
        factory.getPolicy().setIdleTimeout(TIMEOUT);
        factory.setCreator(new GameWebSocketCreator(accountService,gameMechanics,webSocketService));
    }

}
