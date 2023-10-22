package persistence.persister;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import persistence.entitiy.attribute.GeneralAttribute;
import persistence.entitiy.attribute.id.IdAttribute;
import persistence.sql.dml.builder.SelectQueryBuilder;
import persistence.sql.parser.AttributeParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityPersister {
    private final JdbcTemplate jdbcTemplate;
    private final AttributeParser attributeParser;

    public EntityPersister(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.attributeParser = new AttributeParser();
    }

    public <T> T findById(Class<T> clazz, String id) {
        IdAttribute idAttribute = attributeParser.parseIdAttribute(clazz);
        List<GeneralAttribute> generalAttributes = attributeParser.parseGeneralAttributes(clazz);

        String sql = SelectQueryBuilder.of(clazz).where(idAttribute.getColumnName(), id).prepareStatement();

        return jdbcTemplate.queryForObject(sql, rs -> mapResultSetToEntity(clazz, idAttribute, generalAttributes, rs));
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
