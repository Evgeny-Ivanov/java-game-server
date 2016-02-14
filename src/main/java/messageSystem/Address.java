package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stalker on 12.02.16.
 */
public class Address {//адрес какого нибудь сервиса
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final int id;//уникальный индентификатор сервиса

    public Address(){
        id = ID_GENERATOR.getAndIncrement();
    }

    @Override
    public int hashCode(){
        return id;
    }
}
