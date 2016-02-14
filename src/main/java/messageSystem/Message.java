package messageSystem;

/**
 * Created by stalker on 12.02.16.
 */
public abstract class Message {
    protected final Address to;//кому
    protected final Address from;//от кого

    public Message(Address to, Address from){
        this.from = from;
        this.to = to;
    }

    public Address getTo(){
        return to;
    }

    public Address getFrom(){
        return from;
    }

    public abstract void exec(Abonent abonent);

}
