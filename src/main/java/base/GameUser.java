package base;

/**
 * Created by stalker on 30.01.16.
 */
public class GameUser {//моделька игрока
    private final String myName;
    private int myScore = 0;


    public GameUser(String myName){
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public int getMyScore() {
        return myScore;
    }


    public void incrementMyScore() {
        myScore++;
    }


}
