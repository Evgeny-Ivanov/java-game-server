package frontend.messages;

import frontend.FrontendThread;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by stalker on 13.02.16.
 */
public abstract class MessageToFrontend extends Message{



    public MessageToFrontend(Address to,Address from){
        super(to,from);
    }

    @Override
    public void exec(Abonent abonent){
        System.out.println("MessageToFrontend " + abonent.toString());
        if(abonent instanceof FrontendThread){
            exec((FrontendThread)abonent);
        }
    }

    protected abstract void exec(FrontendThread frontendThread);
}
