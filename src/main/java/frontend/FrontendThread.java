package frontend;

import databaseService.dataSets.UserProfile;
import databaseService.messageSystem.MessageAddSession;
import databaseService.messageSystem.MessageAddUser;
import databaseService.messageSystem.MessageRemoveSession;
import messageSystem.*;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Pack200;

/**
 * Created by stalker on 12.02.16.
 */
public class FrontendThread implements Runnable, Abonent{
    private Address address = new Address();
    private MessageSystem messageSystem;
    private Map<String,UserState> sessionToState = new ConcurrentHashMap<>();

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }

    public FrontendThread(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    public void addUser(String login, UserProfile profile){
        Message message = new MessageAddUser(address, messageSystem.getAddressService().getDBService(),login,profile);
        messageSystem.sendMessage(message);
    }

    public void addSession(String session, UserProfile profile){
        Message message = new MessageAddSession(address,
                messageSystem.getAddressService().getDBService(),session,profile);
        messageSystem.sendMessage(message);
    }

    public void removeSession(String session){
        Message message = new MessageRemoveSession(address,
                messageSystem.getAddressService().getDBService(),session);
        messageSystem.sendMessage(message);
    }

    public UserState getUserState(String sessionId){
        if(!sessionToState.containsKey(sessionId))
            sessionToState.put(sessionId,UserState.SLEEPS);
        return sessionToState.get(sessionId);
    }

    public void setUserState(String sessionId, UserState state){
        if(sessionToState.containsKey(sessionId))
            sessionToState.replace(sessionId,state);
        else sessionToState.put(sessionId,state);
    }

    @Override
    public Address getAddress(){
        return address;
    }

    @Override
    public void run(){
        System.out.println("Start frontend");
        while (true){
            messageSystem.execForAbonent(this);
        }
    }

}
