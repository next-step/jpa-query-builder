package persistence.sql.dml.statement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dialect.ColumnType;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.builder.WhereClauseBuilder;
import persistence.sql.schema.ColumnMeta;
import persistence.sql.schema.EntityClassMappingMeta;
import persistence.sql.schema.EntityObjectMappingMeta;

public class SelectStatementBuilder {

    private final StringBuilder selectStatementBuilder;
    private WhereClauseBuilder whereClauseBuilder;
    private static final String SELECT_FORMAT = "SELECT %s FROM %s";
    private static final String SELECT_ALL_FIELD = "*";
    private static final String SELECT_WHERE_FORMAT = "%s %s";

    private SelectStatementBuilder() {
        this.selectStatementBuilder = new StringBuilder();
    }

    public static SelectStatementBuilder builder() {
        return new SelectStatementBuilder();
    }

    public SelectStatementBuilder select(Object object, ColumnType columnType, String... targetFieldNames) {
        final EntityObjectMappingMeta objectMappingMeta = EntityObjectMappingMeta.of(
            object,
            EntityClassMappingMeta.of(object.getClass(), columnType)
        );

        if (selectStatementBuilder.length() > 0) {
            throw new RuntimeException("select() method must be called only once");
        }

        if (targetFieldNames.length == 0) {
            selectStatementBuilder.append(String.format(SELECT_FORMAT, SELECT_ALL_FIELD, objectMappingMeta.getTableName()));
            return this;
        }

        validateSelectTargetField(objectMappingMeta, targetFieldNames);
        selectStatementBuilder.append(String.format(SELECT_FORMAT, String.join(", ", targetFieldNames), objectMappingMeta.getTableName()));
        return this;
    }

    public SelectStatementBuilder where(WherePredicate predicate) {
        this.whereClauseBuilder = WhereClauseBuilder.builder(predicate);
        return this;
    }

    public SelectStatementBuilder and(WherePredicate predicate) {
        if (this.whereClauseBuilder == null) {
            throw new RuntimeException("where() method must be called");
        }

        this.whereClauseBuilder.and(predicate);
        return this;
    }

    public SelectStatementBuilder or(WherePredicate predicate) {
        if (this.whereClauseBuilder == null) {
            throw new RuntimeException("where() method must be called");
        }

        this.whereClauseBuilder.or(predicate);
        return this;
    }

    public String build() {
        if (selectStatementBuilder.length() == 0) {
            throw new RuntimeException("SelectStatement must start with select()");
        }

        if (this.whereClauseBuilder == null) {
            return selectStatementBuilder.toString();
        }

        return String.format(SELECT_WHERE_FORMAT, selectStatementBuilder, this.whereClauseBuilder.build());
    }

    private static void validateSelectTargetField(EntityObjectMappingMeta objectMappingMeta, String[] targetFieldNames) {
        final List<String> definedFieldNameList = objectMappingMeta.getColumnMetaSet().stream()
            .map(ColumnMeta::getColumnName)
            .collect(Collectors.toList());

        final List<String> undefinedTargetField = Arrays.stream(targetFieldNames)
            .filter(targetFieldName -> !definedFieldNameList.contains(targetFieldName))
            .collect(Collectors.toList());

        if (!undefinedTargetField.isEmpty()) {
            throw new RuntimeException(String.format("%s 필드는 정의되지 않은 필드입니다.", undefinedTargetField));
        }
    }
}
