package databaseService.messageSystem;

import databaseService.DBService;
import databaseService.DBServiceThread;
import databaseService.dataSets.UserProfile;
import frontend.messages.MessageAddUserResult;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by stalker on 12.02.16.
 */
public class MessageAddSession extends MessageToDatabaseService {
    private String session;
    private UserProfile profile;

    public MessageAddSession(Address to, Address from, String session, UserProfile profile){
        super(to,from);
        this.session = session;
        this.profile = profile;
    }

    @Override
    protected void exec(DBServiceThread dbService){
        System.out.println("Frontend addSession");
        boolean result = dbService.getAccountService().addSession(session,profile);
        Message back = new MessageAddUserResult(from,to,result,profile.getLogin());
        dbService.getMessageSystem().sendMessage(back);
    }

}
