package persistence.sql.ddl.builder;

import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.TypeDialect;
import persistence.sql.meta.ColumnMeta;
import persistence.sql.meta.ColumnMetas;

import java.util.ArrayList;
import java.util.List;

import static persistence.sql.util.StringConstant.*;

public class ColumnBuilder {

    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String NOT_NULL = "NOT NULL";

    private final Dialect dialect;
    private final ColumnMetas columnMetas;

    public ColumnBuilder(Dialect dialect, ColumnMetas columnMetas) {
        this.dialect = dialect;
        this.columnMetas = columnMetas;
    }

    public String buildColumnDefinition() {
        return String.join(COLUMN_JOIN, toSql());
    }

    private List<String> toSql() {
        List<String> sqlElements = new ArrayList<>();
        ColumnMetas exceptTransientColumns = columnMetas.exceptTransient();
        exceptTransientColumns.forEach(
                columnMeta -> sqlElements.add(toSql(columnMeta))
        );
        return sqlElements;
    }

    private String toSql(ColumnMeta columnMeta) {
        return new StringBuilder()
                .append(columnMeta.getColumnName())
                .append(BLANK)
                .append(getSqlType(columnMeta))
                .append(getGenerationStrategy(columnMeta))
                .append(getPrimaryKey(columnMeta))
                .append(getNullable(columnMeta))
                .toString();
    }


    private String getSqlType(ColumnMeta columnMeta) {
        TypeDialect typeDialect = dialect.getTypeDialect();
        return typeDialect.getSqlType(columnMeta.getJavaType());
    }

    private String getGenerationStrategy(ColumnMeta columnMeta) {
        if (columnMeta.isGenerationTypeIdentity()) {
            return BLANK + dialect.getGenerationTypeIdentity();
        }
        return EMPTY_STRING;
    }

    private String getPrimaryKey(ColumnMeta columnMeta) {
        if (columnMeta.isId()) {
            return BLANK + PRIMARY_KEY;
        }
        return EMPTY_STRING;
    }

    private String getNullable(ColumnMeta columnMeta) {
        if (columnMeta.isNullable()) {
            return EMPTY_STRING;
        }
        return BLANK + NOT_NULL;
    }

}
