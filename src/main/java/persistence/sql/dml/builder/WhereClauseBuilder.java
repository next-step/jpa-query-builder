package persistence.sql.dml.builder;

import persistence.sql.dml.ColumnValues;
import persistence.sql.meta.EntityMeta;
import persistence.sql.util.StringConstant;

import java.util.List;

public class WhereClauseBuilder {

    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";

    private final EntityMeta entityMeta;
    private ColumnValues columnValues;

    private WhereClauseBuilder(EntityMeta entityMeta) {
        this.entityMeta = entityMeta;
        this.columnValues = ColumnValues.emptyValues();
    }

    public static WhereClauseBuilder builder(EntityMeta entityMeta) {
        if (entityMeta == null) {
            throw new IllegalArgumentException("Entity Meta 정보는 필수 입력대상입니다.");
        }
        return new WhereClauseBuilder(entityMeta);
    }

    public WhereClauseBuilder appendPkClause(Object entity) {
        ColumnValues idColumnValues = ColumnValues.ofId(entity);
        columnValues.putAll(idColumnValues);
        return this;
    }

    public String build() {
        if (columnValues.isEmpty()) {
            return StringConstant.EMPTY_STRING;
        }
        return new StringBuilder()
                .append(WHERE)
                .append(String.join(AND, buildValueConditions()))
                .toString();
    }

    public String buildPkClause(Object pkObject) {
        this.columnValues = ColumnValues.ofId(entityMeta, pkObject);
        return build();
    }

    private List<String> buildValueConditions() {
        return columnValues.buildValueConditions();
    }

}
