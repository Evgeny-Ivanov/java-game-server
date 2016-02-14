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

    private UserProfile profile;
    private boolean result;

    public MessageAddSessionResult(Address to, Address from, boolean result, UserProfile profile){
        super(to,from);
        this.result = result;
        this.profile = profile;
    }

    @Override
    public void exec(FrontendThread frontend){
        System.out.println("Frontend addSessionResult");
        if(result)
            frontend.setUserState(profile.getLogin(), UserState.SUCCESSFUL_AUTHORIZED);
        else
            frontend.setUserState(profile.getLogin(), UserState.UNSUCCESSFUL_AUTHORIZED);
    }
}
