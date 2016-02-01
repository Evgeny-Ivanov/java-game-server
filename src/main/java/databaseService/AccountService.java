package databaseService;

import databaseService.dataSets.UserProfile;

/**
 * Created by stalker on 01.02.16.
 */
public interface AccountService {
    boolean addUser(String login, UserProfile profile);

    boolean addSession(String session, UserProfile profile);

    UserProfile getUser(String login);

    UserProfile getSession(String session);

    boolean removeSession(String session);

    int getCountUsers();

    int getCountSessions();

}
