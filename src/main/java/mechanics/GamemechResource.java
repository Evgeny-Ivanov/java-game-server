package mechanics;

import resourceSystem.Resource;

/**
 * Created by stalker on 31.01.16.
 */
public class GamemechResource implements Resource {
    private int countPlayers;
    private int timeGame;
    private int stepTime;

    public int getCountPlayers(){
        return countPlayers;
    }

    public int getTimeGame(){
        return timeGame;
    }

    public int getStepTime(){
        return stepTime;
    }
}
