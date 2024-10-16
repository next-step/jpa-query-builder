package persistence.sql.dml.insert;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InsertQueryBuilder {
    private String tableName;
    private String columnClause;
    private String valueClause;

    private InsertQueryBuilder() {
    }

    public static InsertQueryBuilder newInstance() {
        return new InsertQueryBuilder();
    }

    public InsertQueryBuilder entity(Object entity) throws IllegalAccessException {
        this.tableName = NameUtils.getTableName(entity.getClass());
        this.setColumnClause(entity.getClass());
        this.setValueClause(entity);
        return this;
    }

    private void setColumnClause(Class<?> clazz) {
        StringBuilder stringBuilder = new StringBuilder("(");

        Field[] managedFields = getManagedFields(clazz);
        for (Field field : managedFields) {
            stringBuilder
                    .append(NameUtils.getColumnName(field))
                    .append(", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(")");

        this.columnClause = stringBuilder.toString();
    }

    private void setValueClause(Object object) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder("(");

        Class<?> clazz = object.getClass();
        Field[] fields = getManagedFields(clazz);
        for (Field field : fields) {
            field.setAccessible(true);
            stringBuilder
                    .append(field.get(object))
                    .append(", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(")");
        this.valueClause = stringBuilder.toString();
    }

    private Field[] getManagedFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isManagedField)
                .toArray(Field[]::new);
    }

    private boolean isManagedField(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return false;
        }
        if (field.isAnnotationPresent(Id.class)
                && field.isAnnotationPresent(GeneratedValue.class)
                && GenerationType.IDENTITY.equals(field.getAnnotation(GeneratedValue.class).strategy())) {
            return false;
        }
        return true;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("insert into {TABLE_NAME} ")
                .append(this.columnClause)
                .append(" values ")
                .append(this.valueClause)
                .append(";");

        return stringBuilder.toString().replace("{TABLE_NAME}", this.tableName);
    }
}
