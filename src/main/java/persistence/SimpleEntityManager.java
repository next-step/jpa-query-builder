package persistence;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import jdbc.JdbcTemplate;
import persistence.sql.dml.DmlGenerator;
import persistence.sql.meta.Column;
import persistence.sql.meta.Columns;

public class SimpleEntityManager implements EntityManager {

    private final DmlGenerator ddlGenerator;
    private final JdbcTemplate jdbcTemplate;


    private SimpleEntityManager(JdbcTemplate jdbcTemplate) {
        this.ddlGenerator = DmlGenerator.from();
        this.jdbcTemplate = jdbcTemplate;
    }

    public static SimpleEntityManager from(JdbcTemplate jdbcTemplate) {
        return new SimpleEntityManager(jdbcTemplate);
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return jdbcTemplate.queryForObject(ddlGenerator.generateSelectQuery(clazz, id),
            resultSet -> mapResultSetToEntity(resultSet, clazz));
    }

    @Override
    public void persist(Object entity) {
        jdbcTemplate.execute(ddlGenerator.generateInsertQuery(entity));
    }

    @Override
    public void remove(Object entity) {
        Columns columns = Columns.from(entity.getClass().getDeclaredFields());
        jdbcTemplate.execute(ddlGenerator.generateDeleteQuery(entity.getClass(),
            columns.getIdColumn().getFieldValue(entity)));
    }

    private <T> T mapResultSetToEntity(ResultSet resultSet, Class<T> clazz) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            Columns columns = Columns.from(clazz.getDeclaredFields());
            columns.getColumns()
                .forEach(column -> setFieldValue(resultSet, t, column));
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void setFieldValue(ResultSet resultSet, T t, Column column) {
        Field field = column.getField();
        field.setAccessible(true);
        try {
            field.set(t, FieldType.map(resultSet, column.getColumnName(), field.getType()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
