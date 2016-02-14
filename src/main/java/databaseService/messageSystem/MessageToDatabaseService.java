package databaseService.messageSystem;

import databaseService.DBService;
import databaseService.DBServiceThread;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by stalker on 12.02.16.
 */
public abstract class MessageToDatabaseService extends Message {

    public MessageToDatabaseService(Address to, Address from){
        super(to,from);
    }

    @Override
    public void exec(Abonent abonent){
        System.out.println("MessageToDatabaseService " + abonent.toString());
        if(abonent instanceof DBServiceThread){
            exec((DBServiceThread) abonent);
        }
    }

    protected abstract void exec(DBServiceThread dbService);

}
