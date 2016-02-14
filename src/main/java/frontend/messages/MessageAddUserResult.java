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
    UserProfile profile;

    public MessageAddUserResult(Address to, Address from, boolean result, UserProfile profile){
        super(to,from);
        this.result = result;
    }

    @Override
    protected void exec(FrontendThread frontendThread){
        System.out.println("Frontend addSessionResult");
        if(result)
            frontendThread.setUserState(profile.getLogin(),UserState.SUCCESSFUL_REGISTERED);
        else
            frontendThread.setUserState(profile.getLogin(),UserState.UNSUCCESSFUL_REGISTERED);
    }

}
