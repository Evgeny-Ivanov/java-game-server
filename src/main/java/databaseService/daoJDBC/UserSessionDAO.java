package databaseService.daoJDBC;

import databaseService.helpers.Executor;
import databaseService.dataSets.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by stalker on 01.02.16.
 */
public class UserSessionDAO {
    Connection connection;
    public UserSessionDAO(Connection connection){
        this.connection = connection;
    }

    public void createDB() throws SQLException {
        Executor executor = new Executor();
        executor.execUpdate(connection, "CREATE TABLE sessions(session CHAR(50), login CHAR(50),PRIMARY KEY (session,login));");
    }

    public boolean addSession(String session, UserProfile profile) throws SQLException {
        Executor executor = new Executor();
        StringBuilder query = new StringBuilder();
        query.
                append("INSERT IGNORE INTO sessions(session,login)").
                append(" VALUES (").
                append('\"' + session + "\",").
                append('\"' + profile.getLogin() + '\"').
                append(");");
        System.out.println(query.toString());
        int countRow = executor.execUpdate(connection, query.toString());
        return countRow != 0;
    }

    public UserProfile getSession(String session) throws SQLException{
        Executor executor = new Executor();
        StringBuilder query = new StringBuilder();
        query.
                append("SELECT users.login,users.password,users.email FROM users JOIN sessions ON users.login = sessions.login").
                append(" WHERE session = ").
                append('\"'+ session +'\"').
                append(';');
        System.out.println(query.toString());
        return executor.execQuery(connection,query.toString(),result -> {
            if(result.next())
                return new UserProfile(result.getString(1),result.getString(2),result.getString(3));
            return null;
        });
    }

    public boolean removeSession(String session) throws SQLException{
        Executor executor = new Executor();
        StringBuilder query = new StringBuilder();
        query.
                append("DELETE FROM sessions").
                append(" WHERE session = ").
                append('\"'+ session +'\"').
                append(';');

        int count = executor.execUpdate(connection,query.toString());
        return count != 0;
    }

    public int getCountSessions() throws SQLException{
        Executor executor = new Executor();
        String query = "SELECT count(*) FROM sessions ";
        return executor.execQuery(connection,query,result -> {
            result.next();
            return result.getInt(1);
        });
    }

    public void removeAllSessions() throws SQLException{
        Executor executor = new Executor();
        String query = "DELETE FROM sessions;";
        executor.execUpdate(connection,query);
    }

}
