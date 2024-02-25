package persistence.sql.domain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryResult {

    private final ResultSet resultSet;

    private final DatabaseTable table;

    public QueryResult(ResultSet resultSet, DatabaseTable table) {
        this.resultSet = resultSet;
        this.table = table;
    }

    public <T> T getSingleEntity(Class<T> entityClassType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> entities = getEntities(entityClassType);
        if (entities.size() > 1){
            throw new SQLDataException("duplicate id exist in database");
        }
        if (entities.isEmpty()){
            return null;
        }
        return entities.get(0);
    }

    private <T> List<T> getEntities(Class<T> entityClassType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        List<T> result = new ArrayList<>();
        while(resultSet.next()){
            T entity = getEntity(entityClassType);
            result.add(entity);
        }
        return result;
    }

    private <T> T getEntity(Class<T> entityClassType) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        T entity = entityClassType.getConstructor().newInstance();
        table.getColumns().forEach(column-> setEntityFieldValue(entityClassType, entity, column));
        return entity;
    }

    private <T> void setEntityFieldValue(Class<T> entityClassType, T entity, DatabaseColumn column) {
        String jdbcColumnName = column.getJdbcColumnName();
        String javaFieldName = column.getJavaFieldName();
        try {
            Object value = resultSet.getObject(jdbcColumnName);
            Field field = entityClassType.getDeclaredField(javaFieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
