package databaseService.messageSystem;

import databaseService.DBService;
import databaseService.DBServiceThread;
import databaseService.dataSets.UserProfile;
import frontend.messages.MessageAddUserResult;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.AddressService;
import messageSystem.Message;

/**
 * Created by stalker on 12.02.16.
 */
public class MessageAddUser extends MessageToDatabaseService{
    private String login;
    private UserProfile profile;

    public MessageAddUser(Address to, Address from, String login, UserProfile profile){
        super(to,from);
    }

    @Override
    protected void exec(DBServiceThread dbService){
        System.out.println("DBService addUser");
        boolean result = dbService.getAccountService().addUser(login,profile);
        Message back = new MessageAddUserResult(from,to,result,);
        dbService.getMessageSystem().sendMessage(back);
    }
}
