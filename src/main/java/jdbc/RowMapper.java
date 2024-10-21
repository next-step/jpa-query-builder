package jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
    T mapRow(final ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
