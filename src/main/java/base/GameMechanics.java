package base;

/**
 * Created by stalker on 30.01.16.
 */
public interface GameMechanics {

    void addUser(String user);

    void incrementScore(String userName);

    void run();
}
