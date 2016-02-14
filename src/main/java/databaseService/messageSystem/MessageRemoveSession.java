package databaseService.messageSystem;

import databaseService.DBService;
import databaseService.DBServiceThread;
import databaseService.dataSets.UserProfile;
import frontend.messages.MessageRemoveSessionResult;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by stalker on 12.02.16.
 */
public class MessageRemoveSession extends MessageToDatabaseService{
    String session;

    public MessageRemoveSession(Address to, Address from, String session){
        super(to,from);
        this.session = session;
    }

    @Override
    protected void exec(DBServiceThread dbService){
        System.out.println("DBService removeSession");
        boolean result = dbService.getAccountService().removeSession(session);
        UserProfile profile = dbService.getAccountService().getSession(session);
        Message message = new MessageRemoveSessionResult(from,to,result,profile);
    }
}
