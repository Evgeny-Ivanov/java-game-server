package frontend.game;

import base.GameMechanics;
import base.WebSocketService;
import databaseService.AccountService;
import databaseService.dataSets.UserProfile;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import javax.servlet.http.HttpSession;


/**
 * Created by stalker on 29.01.16.
 */
public class GameWebSocketCreator implements WebSocketCreator {
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocketCreator(AccountService accountService, GameMechanics gameMechanics, WebSocketService webSocketService){
        this.accountService = accountService;
        this.gameMechanics =  gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest request, ServletUpgradeResponse response){
        HttpSession session = request.getHttpServletRequest().getSession();
        UserProfile profile = accountService.getSession(session.getId());
        GameWebSocket socket = new GameWebSocket(profile.getLogin(),gameMechanics,webSocketService);
        return socket;
    }
}
