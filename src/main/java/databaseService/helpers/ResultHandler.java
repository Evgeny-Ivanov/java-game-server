package databaseService.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by stalker on 01.02.16.
 */
public interface ResultHandler<T> {
    public T handle(ResultSet result) throws SQLException;
}
