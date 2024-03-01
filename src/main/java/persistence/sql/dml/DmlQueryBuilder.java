package persistence.sql.dml;

import domain.EntityMetaData;
import domain.H2GenerationType;
import domain.dialect.Dialect;
import domain.vo.ColumnName;
import domain.vo.ColumnValue;
import domain.vo.JavaMappingType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import static domain.constants.CommonConstants.COMMA;

public class DmlQueryBuilder {

    private final JavaMappingType javaMappingType;
    private final Dialect dialect;
    private final EntityMetaData entityMetaData;

    private static final String INSERT_DATA_QUERY = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String FIND_ALL_QUERY = "SELECT * FROM %s;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM %s WHERE %s = %s;";
    private static final String DELETE_QUERY = "DELETE %s WHERE %s = %s;";

    public DmlQueryBuilder(Dialect dialect, EntityMetaData entityMetaData) {
        this.javaMappingType = new JavaMappingType();
        this.dialect = dialect;
        this.entityMetaData = entityMetaData;
    }

    public String insertQuery(Object object) {
        return String.format(INSERT_DATA_QUERY, entityMetaData.getTableName(), columnsClause(object.getClass()),
                valueClause(object));
    }

    public String findAllQuery(Class<?> clazz) {
        return String.format(FIND_ALL_QUERY, entityMetaData.getTableName());
    }

    public String findByIdQuery(Class<?> clazz, Object condition) {
        return String.format(FIND_BY_ID_QUERY, entityMetaData.getTableName(), entityMetaData.getIdField(), condition);
    }

    public String deleteQuery(Class<?> clazz, Field field, Object value) {
        LinkedList<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toCollection(LinkedList::new));

        ColumnName columnName = new ColumnName(fields, field);
        ColumnValue columnValue = new ColumnValue(javaMappingType, javaMappingType.getJavaTypeByClass(clazz), value);

        return String.format(DELETE_QUERY, entityMetaData.getTableName(), columnName.getName(), columnValue.getValue());
    }

    public String deleteByIdQuery(Object entity) {
        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .collect(Collectors.toCollection(LinkedList::new)).stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("primary key 값이 없습니다."));

        Object idFieldValue;
        try {
            idField.setAccessible(true);
            idFieldValue = idField.get(entity);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Field 정보가 존재하지 않습니다.");
        }

        return deleteQuery(entity.getClass(), idField, idFieldValue);
    }

    private String columnsClause(Class<?> clazz) {
        LinkedList<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toCollection(LinkedList::new));

        return fields.stream()
                .filter(field -> !isTransientField(field))
                .map(field -> new ColumnName(fields, field))
                .map(ColumnName::getName)
                .reduce((o1, o2) -> String.join(COMMA, o1, String.valueOf(o2)))
                .orElseThrow(() -> new IllegalStateException("Id 혹은 Column 타입이 없습니다."));
    }

    private String valueClause(Object object) {
        LinkedList<Field> fields = Arrays.stream(object.getClass().getDeclaredFields())
                .collect(Collectors.toCollection(LinkedList::new));

        return fields.stream()
                .filter(field -> !isTransientField(field))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        Object fieldValue = field.get(object);

                        //Id 필드 check
                        if (entityMetaData.isIdField(field)) {
                            isValidIdFieldValue(field, fieldValue);
                        }

                        //Column 필드, 어노테이션이 없는 필드
                        if (isColumnField(field)) {
                            isValidColumnFieldValue(field, fieldValue);
                        }

                        if (Objects.isNull(fieldValue)) {
                            return new ColumnValue(javaMappingType, null, null);
                        }

                        Integer javaTypeByClass = javaMappingType.getJavaTypeByClass(field.getType());
                        String columnDefine = dialect.getColumnDefine(javaTypeByClass);

                        fieldValue = columnDefine.equals("varchar") ? "'" + fieldValue + "'" : fieldValue;
                        return new ColumnValue(javaMappingType, javaTypeByClass, fieldValue);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("필드 정보를 가져올 수 없습니다.");
                    }
                })
                .map(ColumnValue::getValue)
                .reduce((o1, o2) -> String.join(COMMA, String.valueOf(o1), String.valueOf(o2)))
                .orElseThrow(() -> new IllegalStateException("Id 혹은 Column 타입이 없습니다."))
                .toString();
    }

    private boolean isColumnField(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    private boolean isTransientField(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private void isValidIdFieldValue(Field field, Object fieldValue) {
        if (Objects.isNull(fieldValue) && !isGenerationTypeAutoOrIdentity(field)) {
            throw new IllegalArgumentException("fieldValue 가 null 이어서는 안됩니다.");
        }
    }

    //TODO 다른 GenerationType 에 대한 검증도 필요합니다. AUTO, IDENTITY 가 아닐 경우
    private boolean isGenerationTypeAutoOrIdentity(Field field) {
        H2GenerationType generationType = getGenerationType(field);
        return Objects.nonNull(generationType) && (generationType.equals(H2GenerationType.AUTO)
                || generationType.equals(H2GenerationType.IDENTITY));
    }

    private H2GenerationType getGenerationType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return H2GenerationType.from(field.getAnnotation(GeneratedValue.class).strategy());
        }
        return null;
    }

    //TODO 길이 체크 리팩토링이 필요한 부분
    private void isValidColumnFieldValue(Field field, Object fieldValue) {
        if (Objects.isNull(fieldValue) && !field.getAnnotation(Column.class).nullable()) {
            throw new IllegalArgumentException("fieldValue 가 null 이어서는 안됩니다.");
        }
    }
}
