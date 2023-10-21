package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntitySingleMapper<T> extends AbstractRowsMapper<T> {

    public EntitySingleMapper(Class<T> tClass) {
        super(tClass);
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        if (hasNext(resultSet)) {
            return mapEntity(tClass, resultSet);
        }
        return null;
    }

    private boolean hasNext(ResultSet resultSet) {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }


}
