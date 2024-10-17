package orm.row_mapper;

import jdbc.RowMapper;
import orm.TableEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 아직 구현안됨. 2-2단계에서 진행한다.
 * @param <T>
 */
public class DefaultRowMapper<T> implements RowMapper<T> {

    private final TableEntity<T> tableEntity;

    public DefaultRowMapper(Class<T> clazz) {
        this.tableEntity = new TableEntity<>(clazz);
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        return null;
    }
}
