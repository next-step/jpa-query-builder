package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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

    public Optional<T> mapToRowOptional(ResultSet resultSet) {
        return Optional.ofNullable(mapRow(resultSet));
    }

    private boolean hasNext(ResultSet resultSet) {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }


}
