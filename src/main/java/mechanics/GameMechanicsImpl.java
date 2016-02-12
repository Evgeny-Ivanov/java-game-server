package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import org.eclipse.jetty.util.ArrayQueue;
import resourceSystem.ResourceContext;

import java.util.*;

/**
 * Created by stalker on 30.01.16.
 */
public class GameMechanicsImpl extends Thread implements GameMechanics{
    private GamemechResource gamemechResource;
    private Queue<String> waiter = new ArrayQueue<>();
    private Set<GameSession> sessions = new HashSet<>();
    private WebSocketService webSocketService;
    private Map<String, GameSession> nameToGame = new HashMap<>();

    public GameMechanicsImpl(WebSocketService webSocketService) {
        gamemechResource = (GamemechResource)ResourceContext.getInstance().get(GamemechResource.class);
        this.webSocketService = webSocketService;
    }

    @Override
    public void addUser(String user) {
        waiter.add(user);
        if(waiter.size() >= gamemechResource.getCountPlayers()) {
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
        System.out.println("start GameMechanics");
        while(!Thread.interrupted()){
            gmStep();
            try {
                Thread.sleep(gamemechResource.getStepTime());
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void gmStep() {
        for(GameSession session : sessions){
            if(session.getSessinTime() > gamemechResource.getTimeGame()){
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
