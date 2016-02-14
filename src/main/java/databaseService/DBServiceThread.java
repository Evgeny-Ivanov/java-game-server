package databaseService;

import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;

/**
 * Created by stalker on 12.02.16.
 */
public class DBServiceThread implements Runnable, Abonent{
    private Address address = new Address();
    private AccountService dbService = new DBServiceHibernate();
    private MessageSystem messageSystem;

    public DBServiceThread(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    @Override
    public Address getAddress(){
        return address;
    }

    @Override
    public void run(){
        System.out.println("Start DBService");
        while (true){
            messageSystem.execForAbonent(this);
        }
    }

    public AccountService getAccountService(){
        return dbService;
    }

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }
}
