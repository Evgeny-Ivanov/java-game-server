package frontend.game;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Set;

/**
 * Created by stalker on 29.01.16.
 */
@WebSocket
public class GameWebSocket {

    private Set<GameWebSocket> users;
    private Session thisSession;

    public GameWebSocket(Set<GameWebSocket> users){
        this.users = users;
    }

    @OnWebSocketConnect
    public void onOpen(Session session){
        thisSession = session;
        System.out.println("Open");
    }

    @OnWebSocketMessage
    public void onMessage(String data){
        System.out.println("Message");
        for(GameWebSocket user : users){
            try {
                user.getSession().getRemote().sendString(data);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        System.out.println("Close. код: " + statusCode + "причина: " + reason);
    }

    public Session getSession(){
        return thisSession;
    }

}
