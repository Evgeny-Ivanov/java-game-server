package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import org.eclipse.jetty.util.ArrayQueue;

import java.util.*;

/**
 * Created by stalker on 30.01.16.
 */
public class GameMechanicsImpl implements GameMechanics{
    private static final int STEP_TIME = 100;
    private static final int GAME_TIME = 1 * 1000;
    private Queue<String> waiter = new ArrayQueue<>();
    private Set<GameSession> sessions = new HashSet<>();
    private WebSocketService webSocketService;
    private Map<String, GameSession> nameToGame = new HashMap<>();

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public void addUser(String user) {
        waiter.add(user);
        if(waiter.size() >= GameSession.countUser) {
            System.out.println("start game: count: " + waiter.size());
            startGame();
        }
    }

    @Override
    public void incrementScore(String userName) {
        GameSession session = nameToGame.get(userName);
        GameUser user = session.getUserByName(userName);
        user.incrementMyScore();
        session.setWinner(user);
        Iterator<GameUser> iteratorUser = session.getUsers().values().iterator();
        while (iteratorUser.hasNext()){
            GameUser enemyUser = iteratorUser.next();
            webSocketService.notifyMyNewScore(user,enemyUser);
        }
    }

    @Override
    public void run() {
        while(true){
            gmStep();
            try {
                Thread.sleep(STEP_TIME);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void gmStep() {
        for(GameSession session : sessions){
            if(session.getSessinTime() > GAME_TIME){
                System.out.println("gameover");
                Iterator<GameUser> iteratorUser = session.getUsers().values().iterator();
                while (iteratorUser.hasNext()){
                    webSocketService.notifyGameOver(iteratorUser.next(),session.getWinner().getMyName());
                }
                sessions.remove(session);
            }
        }
    }

    public void startGame(){
        GameSession gameSession = new GameSession(waiter);
        sessions.add(gameSession);
        for (String name : waiter){
            GameUser user = gameSession.getUserByName(name);
            nameToGame.put(name, gameSession);
        }
        webSocketService.notifyStartGame(gameSession.getUsers());
        waiter.clear();
    }

}
