package databaseService.daoJDBC;

import databaseService.helpers.Executor;
import databaseService.dataSets.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by stalker on 01.02.16.
 */
public class UserProfileDAO {
    Connection connection;

    public UserProfileDAO(Connection connection){
        this.connection = connection;
    }

    public void createDB() throws SQLException {
        Executor executor = new Executor();
        executor.execUpdate(connection, "CREATE TABLE users(login CHAR(50), password CHAR(50), email CHAR(50),PRIMARY KEY (login))");
    }

    public boolean addUser(String login, UserProfile profile) throws SQLException{
        Executor executor = new Executor();
        StringBuilder query = new StringBuilder();
        query.
                append("INSERT INTO users(login,password,email)").
                append("VALUES (").
                append('\"' + profile.getLogin() + "\",").
                append('\"' + profile.getPassword() + "\",").
                append('\"'+profile.getEmail()+'\"').
                append(");");
        System.out.println(query.toString());
        int countRow = executor.execUpdate(connection, query.toString());
        return countRow != 0;
    }

    public UserProfile getUser(String login) throws SQLException{
        Executor executor = new Executor();
        StringBuilder query = new StringBuilder();
        query.
                append("SELECT login,password,email FROM users ").
                append("WHERE login = ").
                append('\"'+login+'\"').
                append(';');
        System.out.println(query.toString());
        return executor.execQuery(connection,query.toString(),result -> {
            if(result.next())
                return new UserProfile(result.getString(1), result.getString(2), result.getString(3));
            return null;
        });
    }


    public int getCountUsers() throws SQLException{
        Executor executor = new Executor();
        String query = "SELECT count(*) FROM users ";
        return executor.execQuery(connection,query,result -> {
            result.next();
            return result.getInt(1);
        });
    }

}
