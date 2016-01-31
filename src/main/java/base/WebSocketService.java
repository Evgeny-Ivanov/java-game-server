package base;

import frontend.game.GameWebSocket;

import java.util.Map;

/**
 * Created by stalker on 30.01.16.
 */
public interface WebSocketService {

    void addUser(GameWebSocket socket);

    void notifyMyNewScore(GameUser user,GameUser enemyUser);

    void notifyStartGame(Map<String,GameUser> users);

    void notifyGameOver(GameUser user,String nameWiner);

}
