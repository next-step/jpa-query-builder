package persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import persistence.entity.attribute.AttributeParser;
import persistence.entity.attribute.EntityAttribute;
import persistence.entity.attribute.GeneralAttribute;
import persistence.entity.attribute.id.IdAttribute;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;
    private final AttributeParser attributeParser = new AttributeParser();


    private EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static EntityManagerImpl of(JdbcTemplate jdbcTemplate) {
        return new EntityManagerImpl(jdbcTemplate);
    }

    @Override
    public <T> T findById(Class<T> clazz, String id) {
        return loadAndManageEntity(clazz, id);
    }

    private <T> T loadAndManageEntity(Class<T> clazz, String id) {
        EntityAttribute entityAttribute = EntityAttribute.of(clazz, attributeParser);
        IdAttribute idAttribute = attributeParser.parseIdAttribute(clazz);
        List<GeneralAttribute> generalAttributes = attributeParser.parseGeneralAttributes(clazz);

        String sql = SelectQueryBuilder.of(entityAttribute).where(idAttribute.getColumnName(), id).prepareStatement();

        return jdbcTemplate.queryForObject(sql, rs -> mapResultSetToEntity(clazz, idAttribute, generalAttributes, rs));
    }

    @Override
    public <T> T persist(T instance) {
        EntityAttribute entityAttribute = EntityAttribute.of(instance.getClass(), new AttributeParser());

        String sql = new InsertQueryBuilder().prepareStatement(entityAttribute, instance);

        if (entityAttribute.getIdAttribute().getGenerationType() != null) {
            long key = jdbcTemplate.executeForGeneratedKey(sql);
            try {
                setIdToInstance(instance, entityAttribute, key);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    @Override
    public <T> void remove(T entity) {
        EntityAttribute entityAttribute = EntityAttribute.of(entity.getClass(), attributeParser);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String deleteDML = deleteQueryBuilder.prepareStatement(entityAttribute, getEntityId(entity));
        jdbcTemplate.execute(deleteDML);
    }

    private <T> void setIdToInstance(T instance, EntityAttribute entityAttribute, long key) throws NoSuchFieldException {
        String fieldName = entityAttribute.getIdAttribute().getFieldName();
        Field idField = instance.getClass().getDeclaredField(fieldName);
        idField.setAccessible(true);

        try {
            idField.set(instance, key);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("인스턴스의 ID 필드에 키 값을 할당하는데 실패했습니다.", e);
        }
    }

    private <T> String getEntityId(T entity) {
        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ID 필드를 찾을 수 없습니다."));

        idField.setAccessible(true);

        try {
            Object idValue = idField.get(entity);

            assert idValue != null;

            return String.valueOf(idValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T mapResultSetToEntity(Class<T> clazz, IdAttribute idAttribute, List<GeneralAttribute> generalAttributes, ResultSet rs) {
        try {
            if (!rs.next()) {
                return null;
            }

            T instance = instantiateClass(clazz);

            Field idField = Arrays.stream(clazz.getDeclaredFields()).filter(it ->
                    it.isAnnotationPresent(Id.class)).findFirst().orElseThrow();

            idField.setAccessible(true);
            idField.set(instance, rs.getObject(idAttribute.getColumnName()));

            List<Field> generalFields = Arrays.stream(clazz.getDeclaredFields()).filter(it ->
                    !it.isAnnotationPresent(Id.class) && it.isAnnotationPresent(Column.class)
            ).collect(Collectors.toList());

            for (Field field : generalFields) {
                field.setAccessible(true);
                GeneralAttribute generalAttribute = attributeParser.parseGeneralAttribute(field);
                Object value = rs.getObject(generalAttribute.getColumnName());
                field.set(instance, value);
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T instantiateClass(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("[%s] 클래스 초기화 실패", clazz.getSimpleName()), e);
        }
    }
}
