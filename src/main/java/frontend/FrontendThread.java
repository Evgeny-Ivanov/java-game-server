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
    private Map<String,UserState> loginToState = new ConcurrentHashMap<>();

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }

    public FrontendThread(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    public void addUser(String login, UserProfile profile){
        Message message = new MessageAddUser(
                messageSystem.getAddressService().getDBService(),address,login,profile);
        messageSystem.sendMessage(message);
    }

    public void addSession(String session, UserProfile profile){
        Message message = new MessageAddSession(
                messageSystem.getAddressService().getDBService(),address,session,profile);
        messageSystem.sendMessage(message);
    }

    public void removeSession(String session){
        Message message = new MessageRemoveSession(
                messageSystem.getAddressService().getDBService(),address,session);
        messageSystem.sendMessage(message);
    }

    public UserState getUserState(String login){
        if(!loginToState.containsKey(login))
            loginToState.put(login,UserState.SLEEPS);
        return loginToState.get(login);
    }

    public void setUserState(String login, UserState state){
        if(loginToState.containsKey(login))
            loginToState.replace(login,state);
        else loginToState.put(login,state);
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
