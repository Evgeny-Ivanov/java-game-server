package frontend.messages;

import databaseService.dataSets.UserProfile;
import frontend.FrontendThread;
import frontend.UserState;
import messageSystem.Address;

/**
 * Created by stalker on 13.02.16.
 */
public class MessageRemoveSessionResult extends MessageToFrontend{
    boolean result;
    String login;
    public MessageRemoveSessionResult(Address to, Address from, boolean result, String login){
        super(to,from);
        this.result = result;
        this.login = login;
    }

    @Override
    public void exec(FrontendThread frontend){
        System.out.println("Frontend removeSessionResult");
        if(result)
            frontend.setUserState(login, UserState.SUCCESSFUL_LEAVING);
        else frontend.setUserState(login, UserState.UNSUCCESSFUL_LEAVING);
    }
}
