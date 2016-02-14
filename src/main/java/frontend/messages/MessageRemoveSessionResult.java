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
    UserProfile profile;
    public MessageRemoveSessionResult(Address to, Address from, boolean result, UserProfile profile){
        super(to,from);
        this.result = result;
        this.profile = profile;
    }

    @Override
    public void exec(FrontendThread frontend){
        System.out.println("Frontend addSessionResult");
        if(result)
            frontend.setUserState(profile.getLogin(), UserState.SUCCESSFUL_LEAVING);
        else frontend.setUserState(profile.getLogin(), UserState.UNSUCCESSFUL_LEAVING);
    }
}
