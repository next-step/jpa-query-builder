package persistence.mapper;

import java.sql.ResultSet;

@FunctionalInterface
public interface RowMapper<T> {
    T mapRow(final ResultSet resultSet) throws Exception;
}
