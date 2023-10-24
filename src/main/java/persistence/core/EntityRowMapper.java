package persistence.core;

import jdbc.RowMapper;
import persistence.exception.NotMappingEntityClassException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Set;

public class EntityRowMapper<T> implements RowMapper<T> {

    private static final int DEFAULT_CONSTRUCTOR_PARAMETER_COUNT = 0;

    private final Class<?> clazz;

    private final EntityMetadataModelHolder entityMetadataModelHolder;

    public EntityRowMapper(Class<?> clazz, EntityMetadataModelHolder entityMetadataModelHolder) {
        this.clazz = clazz;
        this.entityMetadataModelHolder = entityMetadataModelHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return null;
            }

            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();

            Constructor<?> defaultConstructor = Arrays.stream(declaredConstructors)
                    .filter(it -> it.getParameterCount() == DEFAULT_CONSTRUCTOR_PARAMETER_COUNT)
                    .findFirst()
                    .orElseThrow(NotMappingEntityClassException::new);

            defaultConstructor.setAccessible(true);

            T entityInstance = (T) defaultConstructor.newInstance();

            Arrays.stream(clazz.getDeclaredFields())
                    .forEach(field -> setEntityFieldValue(entityInstance, resultSet, field));

            return entityInstance;
        } catch (Exception e) {
            throw new NotMappingEntityClassException("not instance of entity class: " + clazz.getName(), e);
        }
    }

    private void setEntityFieldValue(T entityInstance, ResultSet resultSet, Field field) {
        EntityMetadataModel entityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(clazz);
        EntityColumn primaryKeyColumn = entityMetadataModel.getPrimaryKeyColumn();

        field.setAccessible(true);

        try {
            if (primaryKeyColumn.isEqualField(field)) {
                Object fieldValue = resultSet.getObject(primaryKeyColumn.getName());
                field.set(entityInstance, fieldValue);
                return;
            }

            //TODO: 일급컬렉션 활용으로 변경
            Set<EntityColumn> columns = entityMetadataModel.getColumns();

            EntityColumn entityColumn = columns.stream()
                    .filter(it -> it.isEqualField(field))
                    .findFirst()
                    .orElseThrow();

            if (entityColumn.hasTransient()) {
                return;
            }

            Object fieldValue = resultSet.getObject(entityColumn.getName());
            field.set(entityInstance, fieldValue);
        } catch (Exception e) {
            throw new NotMappingEntityClassException("not setting value " + field.getName() + field.getType() + " on entity field", e);
        }
    }
}
