package persistence.sql.dml;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.exception.NotFoundEntityException;
import persistence.sql.dml.AbstractQueryBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InsertQueryBuilder extends AbstractQueryBuilder {

    private static final String COMMA = ", ";

    private static final String OPEN_PARENTHESIS = "(";

    private static final String CLOSE_PARENTHESIS = ")";

    public InsertQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        super(entityMetadataModelHolder);
    }

    public String createInsertQuery(Object entity) {
        EntityMetadataModel entityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(entity.getClass());

        if (entityMetadataModel == null) {
            throw new NotFoundEntityException(entity.getClass());
        }

        return "insert into " +
                entityMetadataModel.getTableName() +
                columnsClause(entityMetadataModel) +
                " values " +
                valuesClause(entity);
    }


    private String columnsClause(EntityMetadataModel entityMetadataModel) {
        StringBuilder builder = new StringBuilder();

        builder.append(OPEN_PARENTHESIS);

        List<String> columnNames = entityMetadataModel.getColumnNames();

        builder.append(String.join(COMMA, columnNames))
                .append(CLOSE_PARENTHESIS);

        return builder.toString();
    }

    private String valuesClause(Object object) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();

        builder.append(OPEN_PARENTHESIS);

        List<String> values = Arrays.stream(declaredFields).filter(notPrimaryKeyOrTransient())
                .map(field -> {
                            try {
                                field.setAccessible(true);
                                return convertToString(field.get(object));
                            } catch (IllegalAccessException e) {
                                throw new IllegalArgumentException("not access field value", e);
                            }
                        }
                ).collect(Collectors.toUnmodifiableList());

        builder.append(String.join(COMMA, values))
                .append(CLOSE_PARENTHESIS);
        return builder.toString();
    }

    private Predicate<Field> notPrimaryKeyOrTransient() {
        return field -> !(field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Transient.class));
    }

    private String convertToString(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }

        return String.valueOf(value);
    }
}
