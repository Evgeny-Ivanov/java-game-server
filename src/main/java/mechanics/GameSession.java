package mechanics;

import base.GameMechanics;
import base.GameUser;

import java.util.*;

/**
 * Created by stalker on 30.01.16.
 */
public class GameSession {
    private final long startTime;
    public static final int countUser = 2;
    private GameUser winner;

    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(Queue<String> names){
        startTime = new Date().getTime();
        System.out.println("Создание комнаты, учасники: ");
        for(String name: names){
            System.out.println(name);
            GameUser newUser = new GameUser(name);
            users.put(name, newUser);

            winner = newUser;
        }
    }

    public long getSessinTime(){
        return new Date().getTime() - startTime;
    }

    public GameUser getWinner(){
            return winner;
    }

    public void setWinner(GameUser newWinner){
        if(winner.getMyScore() < newWinner.getMyScore())
            winner = newWinner;
    }

    public Map<String, GameUser> getUsers(){
        return users;
    }

    public GameUser getUserByName(String name){
        return users.get(name);
    }


}
