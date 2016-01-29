package frontend.game;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by stalker on 29.01.16.
 */
public class GameWebSocketCreator implements WebSocketCreator {
    private Set<GameWebSocket> users = new HashSet<>();

    @Override
    public Object createWebSocket(ServletUpgradeRequest request, ServletUpgradeResponse response){
        GameWebSocket socket = new GameWebSocket(users);
        users.add(socket);
        return socket;
    }
}
