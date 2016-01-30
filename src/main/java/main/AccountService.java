package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stalker on 28.01.16.
 */
public class AccountService {//session - уникальный id сессии
    Map<String, UserProfile> users = new HashMap<>();
    Map<String, UserProfile> sessions = new HashMap<>();

    public AccountService(){
        UserProfile profile1 =  new UserProfile("test1","1","email@mail.ru");
        addUser(profile1.getLogin(),profile1);
        UserProfile profile2 =  new UserProfile("test2","1","email@mail.ru");
        addUser(profile2.getLogin(),profile2);
        UserProfile profile3 =  new UserProfile("test3","1","email@mail.ru");
        addUser(profile3.getLogin(),profile3);
    }

    public boolean addUser(String login, UserProfile profile){
        if(users.containsKey(login)) return false;

        users.put(login, profile);
        return true;
    }

    public boolean addSession(String session, UserProfile profile) {
        if (sessions.containsKey(session)) return false;

        sessions.put(session, profile);
        return true;
    }

    public UserProfile getUser(String login){
        return users.get(login);
    }

    public UserProfile getSession(String session){
        return sessions.get(session);
    }

    public boolean removeSession(String session){
        if(sessions.containsKey(session)){
            sessions.remove(session);
            return true;
        }
        return false;
    }

    public int getCountUsers(){ return users.size(); }

    public int getCountSessions(){ return  sessions.size(); }

}
