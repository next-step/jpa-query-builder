package orm;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.exception.EntityHasNoDefaultConstructorException;
import orm.exception.InvalidEntityException;
import orm.exception.InvalidIdMappingException;
import orm.settings.JpaSettings;
import orm.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 엔티티 클래스로부터 테이블 정보를 추출한 클래스
 *
 * @param <E> @Entity 어노테이션이 붙은 클래스
 */
public class TableEntity<E> {
    private static final Logger logger = LoggerFactory.getLogger(TableEntity.class);

    private final String tableName;

    private final E entity;
    private final Class<E> tableClass;

    // columns
    private final TablePrimaryField id;
    private final List<TableField> allFields;

    // settings
    private final JpaSettings jpaSettings;

    public TableEntity(Class<E> entityClass, JpaSettings settings) {
        throwIfNotEntity(entityClass);
        this.jpaSettings = settings;
        this.tableName = extractTableName(entityClass);
        this.tableClass = entityClass;
        this.entity = createNewInstance(entityClass);
        this.id = extractId(entityClass);
        this.allFields = extractAllPersistenceFields(entityClass);
    }

    public TableEntity(E entity, JpaSettings settings) {
        Class<E> entityClass = (Class<E>) entity.getClass();
        throwIfNotEntity(entityClass);
        this.jpaSettings = settings;
        this.tableName = extractTableName(entityClass);
        this.tableClass = entityClass;
        this.entity = entity;
        this.id = extractId(entityClass);
        this.allFields = extractAllPersistenceFields(entityClass);
    }

    public TableEntity(Class<E> entityClass) {
        this(entityClass, JpaSettings.ofDefault());
    }

    public TableEntity(E entity) {
        this(entity, JpaSettings.ofDefault());
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

    // id(pk) 생성 전략
    public GenerationType getIdGenerationType() {
        GeneratedValue generatedValue = getId().getGeneratedValue();
        if (generatedValue == null) {
            return null;
        }
        return generatedValue.strategy();
    }

    // id 제외 모든 컬럼
    public List<TableField> getNonIdFields() {
        return allFields.stream()
                .filter(field -> !field.isId())
                .toList();
    }

    // id 포함 모든 컬럼
    public List<TableField> getAllFields() {
        return allFields;
    }

    public Class<E> getTableClass() {
        return tableClass;
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

        return new TablePrimaryField(idList.getFirst(), entity, jpaSettings);
    }

    /**
     * 모든 영속성 필드 추출
     *
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
                                ? new TablePrimaryField(declaredField, entity, jpaSettings)
                                : new TableField(declaredField, entity, jpaSettings)
                );
            }
        }
        return list;
    }

    private E createNewInstance(Class<E> entityClass) {
        try {
            Constructor<E> defaultConstructor = entityClass.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            return defaultConstructor.newInstance();
        } catch (
                NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e
        ) {
            logger.error(e.getMessage());
            throw new EntityHasNoDefaultConstructorException("entity must contain default constructor");
        }
    }
}
