package orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import orm.exception.InvalidEntityException;
import orm.exception.InvalidIdMappingException;
import orm.settings.JpaSettings;
import orm.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 엔티티 클래스로부터 테이블 정보를 추출한 클래스
 *
 *  @param <E> @Entity 어노테이션이 붙은 클래스
 */
public class TableEntity<E> {

    private final String tableName;

    // columns
    private final TablePrimaryField id;
    private final List<TableField> allFields;

    // settings
    private final JpaSettings jpaSettings;

    public TableEntity(Class<E> entityClass, JpaSettings settings) {
        throwIfNotEntity(entityClass);
        this.jpaSettings = settings;
        this.tableName = extractTableName(entityClass);
        this.id = extractId(entityClass);
        this.allFields = extractAllPersistenceFields(entityClass);
    }

    public TableEntity(Class<E> entityClass) {
        this(entityClass, JpaSettings.ofDefault());
    }

    public JpaSettings getJpaSettings() {
        return jpaSettings;
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
     *
     * @param entityClass
     * @throws InvalidEntityException 엔티티가 아닌 경우
     */
    private void throwIfNotEntity(Class<E> entityClass) {
        if (entityClass.getAnnotation(Entity.class) == null) {
            throw new InvalidEntityException(entityClass.getName() + " is not an entity");
        }
    }

    private String extractTableName(Class<E> entityClass) {
        return jpaSettings.getNamingStrategy().namingTable(entityClass);
    }

    /**
     * 엔티티로부터 ID 추출
     *
     * @return TablePrimaryField ID 필드
     * @throws InvalidIdMappingException ID 필드가 없거나 2개 이상인 경우
     */
    private TablePrimaryField extractId(Class<E> entityClass) {
        Field[] declaredFields = entityClass.getDeclaredFields();

        var idList = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .toList();

        if (CollectionUtils.isEmpty(idList) || idList.size() != 1) {
            throw new InvalidIdMappingException("Entity must have one @Id field");
        }

        return new TablePrimaryField(idList.getFirst(), jpaSettings);
    }

    /**
     * 모든 영속성 필드 추출
     * @param entityClass
     * @return
     */
    private List<TableField> extractAllPersistenceFields(Class<E> entityClass) {
        Field[] declaredFields = entityClass.getDeclaredFields();

        List<TableField> list = new ArrayList<>(declaredFields.length);
        for (Field declaredField : declaredFields) {
            boolean transientAnnotated = declaredField.isAnnotationPresent(Transient.class);
            boolean columnAnnotated = declaredField.isAnnotationPresent(Column.class);
            boolean idAnnotated = declaredField.isAnnotationPresent(Id.class);

            if (transientAnnotated && columnAnnotated) {
                throw new InvalidEntityException(String.format(
                        "class %s @Transient & @Column cannot be used in same field"
                        , entityClass.getName())
                );
            }

            if (!transientAnnotated) {
                list.add(
                        idAnnotated
                                ? new TablePrimaryField(declaredField, jpaSettings)
                                : new TableField(declaredField, jpaSettings)
                );
            }
        }
        return list;
    }
}
