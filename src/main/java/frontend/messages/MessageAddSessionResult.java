package frontend.messages;

import databaseService.dataSets.UserProfile;
import frontend.FrontendThread;
import frontend.UserState;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by stalker on 13.02.16.
 */
public class MessageAddSessionResult extends MessageToFrontend{

    private String login;
    private boolean result;

    public MessageAddSessionResult(Address to, Address from, boolean result, String login){
        super(to,from);
        this.result = result;
        this.login = login;
    }

    @Override
    public void exec(FrontendThread frontend){
        System.out.println("Frontend addSessionResult");
        if(result)
            frontend.setUserState(login, UserState.SUCCESSFUL_AUTHORIZED);
        else
            frontend.setUserState(login, UserState.UNSUCCESSFUL_AUTHORIZED);
    }
}
