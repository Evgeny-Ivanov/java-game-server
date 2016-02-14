package messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by stalker on 12.02.16.
 */
public class MessageSystem {

    private final Map<Address,ConcurrentLinkedQueue<Message>> messages = new HashMap<>();

    private final AddressService addressService = new AddressService();

    public void addService(Address address){
        messages.put(address, new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message){
        messages.get(message.getTo()).add(message);
    }

    public AddressService getAddressService(){
        return addressService;
    }

    public void execForAbonent(Abonent abonent){
        ConcurrentLinkedQueue<Message> queue = messages.get(abonent.getAddress());
        while (!queue.isEmpty()){
            Message message = queue.poll();
            message.exec(abonent);
        }
    }
}
