package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableSQLMapper;
import persistence.sql.dml.RowDeleteQueryBuilder;
import persistence.sql.dml.RowFindByIdQueryBuilder;
import persistence.sql.dml.RowInsertQueryBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class JdbcEntityManager implements EntityManager {

    private final QueryBuilder findQueryBuilder;
    private final QueryBuilder insertQueryBuilder;
    private final QueryBuilder deleteQueryBuilder;
    private final JdbcTemplate jdbcTemplate;

    public JdbcEntityManager(Dialect dialect, JdbcTemplate jdbcTemplate) {
        this.findQueryBuilder =  new RowFindByIdQueryBuilder(dialect);
        this.insertQueryBuilder = new RowInsertQueryBuilder(dialect);
        this.deleteQueryBuilder = new RowDeleteQueryBuilder(dialect);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        String sql = this.findQueryBuilder.findBy(id).generateSQLQuery(clazz);
        return jdbcTemplate.queryForObject(sql, resultSet -> {
            if (!resultSet.next()) {
                return null;
            }

            try {
                Object result = getEmptyObject(clazz);
                Map<String, Field> allColumnNamesToFieldMap = TableSQLMapper.getAllColumnNamesToFieldMap(clazz);
                for (String columnName : allColumnNamesToFieldMap.keySet()) {
                    try {
                        Object value = resultSet.getObject(columnName);
                        if (value == null) {
                            continue;
                        }

                        Field field = allColumnNamesToFieldMap.get(columnName);
                        field.setAccessible(true);
                        field.set(result, value);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                return clazz.cast(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Object getEmptyObject(Class<?> clazz) {
        Constructor<?> emptyConstructor = Arrays
            .stream(clazz.getDeclaredConstructors())
            .filter(constructor -> constructor.getParameterCount() == 0)
            .findFirst()
            .orElse(null);
        if (emptyConstructor == null) {
            throw new RuntimeException("Entity must have empty constructor!");
        }

        try {
            return emptyConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object persist(Object entity) {
        String sql = this.insertQueryBuilder.generateSQLQuery(entity);
        this.jdbcTemplate.execute(sql);
        return entity;
    }

    @Override
    public void remove(Object entity) {
        String sql = this.deleteQueryBuilder.generateSQLQuery(entity);
        jdbcTemplate.execute(sql);
    }
}
