package orm;

import jakarta.persistence.*;
import orm.exception.InvalidEntityException;
import orm.exception.InvalidIdMappingException;
import orm.util.CollectionUtils;
import orm.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableEntity<ENTITY> {

    private final Class<ENTITY> entityClass;
    private final String tableName;

    private final TablePrimaryField id;

    private final List<TableField> allFields;

    public TableEntity(Class<ENTITY> entityClass) {
        throwIfNotEntity(entityClass);
        this.entityClass = entityClass;
        this.tableName = extractTableName(entityClass);
        this.id = extractId(entityClass);
        this.allFields = extractAllPersistenceFields(entityClass);
    }

    public String getTableName() {
        return tableName;
    }

    public TablePrimaryField getId() {
        return id;
    }

    public List<TableField> getAllFields() {
        return allFields;
    }

    /**
     * 엔티티가 아닌 경우 예외 발생
     * @throws InvalidEntityException 엔티티가 아닌 경우
     * @param entityClass
     */
    private void throwIfNotEntity(Class<ENTITY> entityClass) {
        if (entityClass.getAnnotation(Entity.class) == null) {
            throw new InvalidEntityException(entityClass.getName() + " is not an entity");
        }
    }

    private String extractTableName(Class<ENTITY> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && StringUtils.isNotBlank(tableAnnotation.name())) {
            return tableAnnotation.name();
        }

        return entityClass.getName();
    }


    /**
     * 엔티티로부터 ID 추출
     * @throws InvalidIdMappingException ID 필드가 없거나 2개 이상인 경우
     * @return TablePrimaryField ID 필드
     */
    private TablePrimaryField extractId(Class<ENTITY> entityClass) {
        Field[] declaredFields = entityClass.getDeclaredFields();

        var idList = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .toList();

        if (CollectionUtils.isEmpty(idList) || idList.size() != 1) {
            throw new InvalidIdMappingException("Entity must have one @Id field");
        }

        return new TablePrimaryField(idList.getFirst());
    }

    private List<TableField> extractAllPersistenceFields(Class<ENTITY> entityClass) {
        Field[] declaredFields = entityClass.getDeclaredFields();

        List<TableField> list = new ArrayList<>(declaredFields.length);
        for (Field declaredField : declaredFields) {
            boolean transientAnnotated = declaredField.isAnnotationPresent(Transient.class);
            boolean columnAnnotated = declaredField.isAnnotationPresent(Column.class);

            if (transientAnnotated && columnAnnotated) {
                throw new InvalidEntityException(String.format(
                        "class %s @Transient & @Column cannot be used in same field"
                        , entityClass.getName())
                );
            }

            if (!transientAnnotated) {
                TableField tableField = new TableField(declaredField);
                list.add(tableField);
            }
        }
        return list;
    }
}
