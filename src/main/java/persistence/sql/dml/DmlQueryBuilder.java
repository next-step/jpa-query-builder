package persistence.sql.dml;

import domain.step2.H2GenerationType;
import domain.step3.dialect.Dialect;
import domain.step3.vo.ColumnName;
import domain.step3.vo.ColumnValue;
import domain.step3.vo.JavaMappingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import static domain.step3.constants.CommonConstants.COMMA;
import static domain.step3.utils.StringUtils.isBlankOrEmpty;

public class DmlQueryBuilder {

    private final JavaMappingType javaMappingType;
    private final Dialect dialect;

    private static final String INSERT_DATA_QUERY = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String FIND_ALL_QUERY = "SELECT * FROM %s;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM %s WHERE %s = %s;";
    private static final String DELETE_QUERY = "DELETE %s WHERE %s = %s;";

    public DmlQueryBuilder(Dialect dialect) {
        this.javaMappingType = new JavaMappingType();
        this.dialect = dialect;
    }

    public String insertQuery(Object object) {
        checkEntityClass(object.getClass());
        return String.format(INSERT_DATA_QUERY, getTableName(object.getClass()), columnsClause(object.getClass()),
                valueClause(object));
    }

    public String findAllQuery(Class<?> clazz) {
        checkEntityClass(clazz);
        return String.format(FIND_ALL_QUERY, getTableName(clazz));
    }

    public String findByIdQuery(Class<?> clazz, Object condition) {
        checkEntityClass(clazz);
        return String.format(FIND_BY_ID_QUERY, getTableName(clazz), getIdField(clazz), condition);
    }

    public String deleteQuery(Class<?> clazz, Field field, Object value) {
        checkEntityClass(clazz);

        LinkedList<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toCollection(LinkedList::new));

        ColumnName columnName = new ColumnName(fields, field);
        ColumnValue columnValue = new ColumnValue(javaMappingType, javaMappingType.getJavaTypeByClass(clazz), value);

        return String.format(DELETE_QUERY, getTableName(clazz), columnName.getName(), columnValue.getValue());
    }

    private void checkEntityClass(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("Entity 클래스가 아닙니다.");
        }
    }

    private String getTableName(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class) ? clazz.getAnnotation(Table.class).name()
                : clazz.getSimpleName().toLowerCase();
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
                        if (isIdField(field)) {
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

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isColumnField(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    private boolean isTransientField(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private String getFieldName(Field field) {
        if (isColumnField(field)) {
            return isBlankOrEmpty(field.getAnnotation(Column.class).name()) ? field.getName()
                    : field.getAnnotation(Column.class).name();
        }
        return field.getName();
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

    private Object getIdField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isIdField)
                .map(this::getFieldName)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Id 필드가 존재하지 않습니다."));
    }
}
