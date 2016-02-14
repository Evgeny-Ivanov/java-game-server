package messageSystem;


import databaseService.DBService;

/**
 * Created by stalker on 13.02.16.
 */
public class AddressService {
    private Address frontend;
    private Address dbService;

    public void registerFrontend(Address frontend){
        this.frontend = frontend;
    }

    public void registerDBService(Address dbService){
        this.dbService = dbService;
    }

    public Address getDBService(){
        return dbService;
    }

    public Address getFrontend(){
        return frontend;
    }

}
