package base;

import frontend.game.GameWebSocket;

/**
 * Created by stalker on 30.01.16.
 */
public interface WebSocketService {

    void addUser(GameWebSocket socket);

    void notifyMyNewScore(GameUser user,GameUser enemyUser);

    void notifyStartGame(GameUser user);

    void notifyGameOver(GameUser user,String nameWiner);

}
