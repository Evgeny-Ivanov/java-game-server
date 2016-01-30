package frontend.game;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Set;

/**
 * Created by stalker on 29.01.16.
 */
@WebSocket
public class GameWebSocket {
    private Session thisSession;
    private String myName;
    private WebSocketService webSocketService;
    private GameMechanics gameMechanics;

    public GameWebSocket(String name, GameMechanics gameMechanics, WebSocketService webSocketService){
        this.myName = name;
        this.webSocketService = webSocketService;
        this.gameMechanics = gameMechanics;
    }

    @OnWebSocketConnect
    public void onOpen(Session session){
        thisSession = session;
        System.out.println("Open");
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    @OnWebSocketMessage
    public void onMessage(String data){
        System.out.println("Message");
        gameMechanics.incrementScore(myName);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        System.out.println("Close. код: " + statusCode + "причина: " + reason);

    }

    public void setScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", user.getMyName());
        jsonStart.put("score", user.getMyScore());
        try {
            thisSession.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void startGame(GameUser user) {

    }

    public void gameOver(GameUser user, String nameWiner) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", nameWiner);
            thisSession.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }


    public Session getSession() {
        return thisSession;
    }

    public String getMyName(){
        return myName;
    }

}
