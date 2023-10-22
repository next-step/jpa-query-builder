package persistence.sql.dml.statement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dialect.ColumnType;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.builder.WhereClauseBuilder;
import persistence.sql.exception.PreconditionRequiredException;
import persistence.sql.schema.ColumnMeta;
import persistence.sql.schema.EntityClassMappingMeta;

public class SelectStatementBuilder {

    private static final String SELECT_FORMAT = "SELECT %s FROM %s";
    private static final String SELECT_ALL_FIELD = "*";
    private static final String SELECT_WHERE_FORMAT = "%s %s;";
    
    private final StringBuilder selectStatementBuilder;
    private WhereClauseBuilder whereClauseBuilder;

    private SelectStatementBuilder() {
        this.selectStatementBuilder = new StringBuilder();
    }

    public static SelectStatementBuilder builder() {
        return new SelectStatementBuilder();
    }

    public SelectStatementBuilder select(Class<?> clazz, ColumnType columnType, String... targetFieldNames) {
        if (selectStatementBuilder.length() > 0) {
            throw new PreconditionRequiredException("select() method must be called only once");
        }

        final EntityClassMappingMeta classMappingMeta = EntityClassMappingMeta.of(clazz, columnType);
        validateSelectTargetField(classMappingMeta, targetFieldNames);

        if (targetFieldNames.length == 0) {
            selectStatementBuilder.append(String.format(SELECT_FORMAT, SELECT_ALL_FIELD, classMappingMeta.tableClause()));
            return this;
        }

        selectStatementBuilder.append(String.format(SELECT_FORMAT, String.join(", ", targetFieldNames), classMappingMeta.tableClause()));
        return this;
    }

    public SelectStatementBuilder where(WherePredicate predicate) {
        this.whereClauseBuilder = WhereClauseBuilder.builder(predicate);
        return this;
    }

    public SelectStatementBuilder and(WherePredicate predicate) {
        if (this.whereClauseBuilder == null) {
            throw new PreconditionRequiredException("where() method must be called");
        }

        this.whereClauseBuilder.and(predicate);
        return this;
    }

    public SelectStatementBuilder or(WherePredicate predicate) {
        if (this.whereClauseBuilder == null) {
            throw new PreconditionRequiredException("where() method must be called");
        }

        this.whereClauseBuilder.or(predicate);
        return this;
    }

    public String build() {
        if (selectStatementBuilder.length() == 0) {
            throw new PreconditionRequiredException("SelectStatement must start with select()");
        }

        if (this.whereClauseBuilder == null) {
            return selectStatementBuilder + ";";
        }

        return String.format(SELECT_WHERE_FORMAT, selectStatementBuilder, this.whereClauseBuilder.build());
    }

    private void validateSelectTargetField(EntityClassMappingMeta entityClassMappingMeta, String[] targetFieldNames) {
        final List<String> definedFieldNameList = entityClassMappingMeta.getMappingColumnMetaList().stream()
            .map(ColumnMeta::getColumnName)
            .collect(Collectors.toList());

        final List<String> undefinedTargetField = Arrays.stream(targetFieldNames)
            .filter(targetFieldName -> !definedFieldNameList.contains(targetFieldName))
            .collect(Collectors.toList());

        if (!undefinedTargetField.isEmpty()) {
            throw new PreconditionRequiredException(String.format("%s 필드는 정의되지 않은 필드입니다.", undefinedTargetField));
        }
    }
}
