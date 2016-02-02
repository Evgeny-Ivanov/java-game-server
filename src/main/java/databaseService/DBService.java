package databaseService;

import databaseService.daoJDBC.UserProfileDAO;
import databaseService.daoJDBC.UserSessionDAO;
import databaseService.dataSets.UserProfile;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by stalker on 01.02.16.
 */
public class DBService implements AccountService{
    Connection connection;
    UserSessionDAO sessionDAO;
    UserProfileDAO profileDAO;

    public DBService(){
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.
                append("jdbc:mysql://").        //db type
                append("localhost:").            //host name
                append("3306/").                //port
                append("db_example?").            //db name
                append("user=root&").            //login
                append("password=1");        //password

            connection = DriverManager.getConnection(url.toString());
        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }

        profileDAO = new UserProfileDAO(connection);
        sessionDAO = new UserSessionDAO(connection);
        try {
            sessionDAO.removeAllSessions();
        } catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("DBService start success");
    }

    @Override
    public boolean addUser(String login, UserProfile profile){
        try {
            return profileDAO.addUser(login, profile);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addSession(String session, UserProfile profile){
        try {
            return sessionDAO.addSession(session, profile);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Nullable
    public UserProfile getUser(String login){
        try {
            return profileDAO.getUser(login);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Nullable
    public UserProfile getSession(String session){
        try {
            return sessionDAO.getSession(session);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean removeSession(String session){
        try {
            return sessionDAO.removeSession(session);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getCountUsers(){
        try {
            return profileDAO.getCountUsers();
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountSessions(){
        try {
            return sessionDAO.getCountSessions();
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

}
