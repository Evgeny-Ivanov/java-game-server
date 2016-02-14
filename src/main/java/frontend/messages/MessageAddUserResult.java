package frontend.messages;

import databaseService.dataSets.UserProfile;
import frontend.FrontendThread;
import frontend.UserState;
import messageSystem.Address;

/**
 * Created by stalker on 13.02.16.
 */
public class MessageAddUserResult extends MessageToFrontend{
    private boolean result;
    String login;

    public MessageAddUserResult(Address to, Address from, boolean result, String login){
        super(to,from);
        this.result = result;
        this.login = login;
    }

    @Override
    protected void exec(FrontendThread frontendThread){
        System.out.println("Frontend addUserResult");
        if(result)
            frontendThread.setUserState(login,UserState.SUCCESSFUL_REGISTERED);
        else
            frontendThread.setUserState(login,UserState.UNSUCCESSFUL_REGISTERED);
    }

}
