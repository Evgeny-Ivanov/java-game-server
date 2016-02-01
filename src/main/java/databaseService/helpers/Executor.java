package databaseService.helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by stalker on 01.02.16.
 */
public class Executor {
    public <T> T execQuery(Connection connection, String query, ResultHandler<T> handler) throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet result = statement.getResultSet();
        T value = handler.handle(result);
        result.close();
        statement.close();

        return value;
    }

    public int execUpdate(Connection connection, String query) throws SQLException{
        Statement statement = connection.createStatement();
        int countRow = statement.executeUpdate(query);
        statement.close();
        return countRow;
    }
}
