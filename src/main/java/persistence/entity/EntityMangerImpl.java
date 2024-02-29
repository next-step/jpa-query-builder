package persistence.entity;

import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import persistence.Person;
import persistence.sql.ddl.exception.IdAnnotationMissingException;
import persistence.sql.dml.*;
import persistence.sql.mapping.ColumnData;
import persistence.sql.mapping.Columns;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EntityMangerImpl implements EntityManger {
    private final JdbcTemplate jdbcTemplate;
    private final GetGeneratedIdStrategy idSetStrategy;

    public EntityMangerImpl(JdbcTemplate jdbcTemplate, GetGeneratedIdStrategy idSetStrategy) {
        this.jdbcTemplate = jdbcTemplate;
        this.idSetStrategy = idSetStrategy;
    }

    @Override
    public Person find(Class<Person> clazz, Long id) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(clazz);
        WhereBuilder builder = new WhereBuilder();
        builder.and(BooleanExpression.eq("id", id));
        String query = selectQueryBuilder.toQuery(builder);

        return jdbcTemplate.queryForObject(query, getRowMapper());
    }

    private static RowMapper<Person> getRowMapper() {
        return rs ->
                new Person(
                        rs.getLong("id"),
                        rs.getString("nick_name"),
                        rs.getInt("old"),
                        rs.getString("email"),
                        null
                );
    }

    @Override
    public Object persist(Object entity) {
        Class<?> clazz = entity.getClass();
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(clazz);

        jdbcTemplate.execute(insertQueryBuilder.toQuery(entity));
        setIdToEntity(entity, clazz);

        return entity;
    }

    private void setIdToEntity(Object entity, Class<?> clazz) {
        Long id = idSetStrategy.getGeneratedId(jdbcTemplate);

        Field idField = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
        idField.setAccessible(true);

        try {
            idField.set(entity, id);
        } catch (IllegalAccessException e) {
            throw new IdAnnotationMissingException();
        }
    }

    @Override
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();
        ColumnData idColumn = Columns.createColumnsWithValue(clazz, entity).getKeyColumn();

        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(clazz);
        WhereBuilder builder = new WhereBuilder();
        builder.and(BooleanExpression.eq(idColumn.getName(), idColumn.getValue()));

        jdbcTemplate.execute(deleteQueryBuilder.toQuery(builder));
    }
}
