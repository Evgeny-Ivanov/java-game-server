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
    String session;

    public MessageAddUserResult(Address to, Address from, boolean result, String session){
        super(to,from);
        this.result = result;
        this.session = session;
    }

    @Override
    protected void exec(FrontendThread frontendThread){
        System.out.println("Frontend addUserResult");
        if(result)
            frontendThread.setUserState(session,UserState.SUCCESSFUL_REGISTERED);
        else
            frontendThread.setUserState(session,UserState.UNSUCCESSFUL_REGISTERED);
    }

}
