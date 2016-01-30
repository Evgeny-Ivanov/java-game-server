package frontend.game;

import base.GameUser;
import base.WebSocketService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stalker on 30.01.16.
 */
public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    public void notifyMyNewScore(GameUser user, GameUser enemyUser) {
        userSockets.get(enemyUser.getMyName()).setScore(user);
    }

    public void notifyStartGame(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(GameUser user, String nameWiner) {
        userSockets.get(user.getMyName()).gameOver(user, nameWiner);
    }

}
