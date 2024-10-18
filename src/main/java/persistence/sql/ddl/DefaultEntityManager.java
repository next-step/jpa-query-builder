package persistence.sql.ddl;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.jetbrains.annotations.NotNull;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.generator.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class DefaultEntityManager implements EntityManager {
    private final Map<Map.Entry<Class, Object>, Object> cache = new HashMap<>();
    private final JdbcTemplate jdbcTemplate;
    private final CreateDDLGenerator createDDLGenerator;
    private final InsertDMLGenerator insertDMLGenerator;
    private final SelectDMLGenerator selectDMLGenerator;
    private final DeleteDMLGenerator deleteDMLGenerator;

    public DefaultEntityManager(JdbcTemplate jdbcTemplate, Dialect dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.createDDLGenerator = new DefaultCreateDDLGenerator(dialect);
        this.insertDMLGenerator = new DefaultInsertDMLGenerator();
        this.selectDMLGenerator = new DefaultSelectDMLGenerator();
        this.deleteDMLGenerator = new DefaultDeleteDMLGenerator();
    }

    @Override
    public <T> T find(Class<T> clazz, Object id) {
        if (containsCache(clazz, id)) {
            return (T) getCache(clazz, id);
        }

        EntityFields entityFields = EntityFields.from(clazz);

        String query = selectDMLGenerator.generateFindById(entityFields, id);

        return (T) jdbcTemplate.queryForObject(query, toObject(clazz, id, entityFields));
    }

    @NotNull
    private <T> RowMapper<Object> toObject(Class<T> clazz, Object id, EntityFields entityFields) {
        return resultSet -> {
            try {
                T t = clazz.newInstance();
                entityFields.getFieldNames().forEach(it -> {
                    Field fieldByName = entityFields.getFieldByName(it);

                    try {
                        Object object = resultSet.getObject(it);

                        fieldByName.setAccessible(true);

                        fieldByName.set(t, object);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } finally {
                        fieldByName.setAccessible(false);
                    }
                });

                putCache(clazz, id, t);

                return t;
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Object persist(Object entity) {
        Class<?> clazz = entity.getClass();
        EntityFields entityFields = EntityFields.from(clazz);

        Field field = entityFields.getIdField();

        try {
            field.setAccessible(true);

            Object id = field.get(entity);

            if (id == null) {
                String query = insertDMLGenerator.generateInsert(field);

                jdbcTemplate.execute(query);
                // id 가져오기
                putCache(clazz, id, entity);
            } else {
                putCache(clazz, id, entity);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
        return null;
    }

    @Override
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();
        EntityFields entityFields = EntityFields.from(clazz);

        Field field = entityFields.getIdField();

        try {
            field.setAccessible(true);

            Object id = field.get(entity);

            removeCache(clazz, id);

            String query = deleteDMLGenerator.generateDeleteById(entityFields, id);

            jdbcTemplate.execute(query);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }

    private boolean containsCache(Class clazz, Object id) {
        AbstractMap.SimpleEntry<Class, Object> entry = new AbstractMap.SimpleEntry<>(clazz, id);

        return cache.containsKey(entry);
    }

    private void putCache(Class clazz, Object id, Object entity) {
        AbstractMap.SimpleEntry<Class, Object> entry = new AbstractMap.SimpleEntry<>(clazz, id);

        cache.put(entry, entity);
    }

    private Object getCache(Class clazz, Object id) {
        AbstractMap.SimpleEntry<Class, Object> entry = new AbstractMap.SimpleEntry<>(clazz, id);

        return cache.get(entry);
    }

    private void removeCache(Class clazz, Object id) {
        AbstractMap.SimpleEntry<Class, Object> entry = new AbstractMap.SimpleEntry<>(clazz, id);

        cache.remove(entry);
    }
}
