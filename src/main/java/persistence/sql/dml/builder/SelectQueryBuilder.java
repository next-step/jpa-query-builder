package persistence.sql.dml.builder;

import persistence.sql.meta.ColumnMetas;
import persistence.sql.meta.EntityMeta;

public class SelectQueryBuilder {

    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";

    private final EntityMeta entityMeta;
    private final WhereClauseBuilder whereClauseBuilder;

    private SelectQueryBuilder(EntityMeta entityMeta) {
        validateEntityAnnotation(entityMeta);
        this.entityMeta = entityMeta;
        this.whereClauseBuilder = WhereClauseBuilder.builder(entityMeta);
    }

    private void validateEntityAnnotation(EntityMeta entityMeta) {
        if (!entityMeta.isEntity()) {
            throw new IllegalArgumentException("Select Query 빌드 대상이 아닙니다.");
        }
    }

    public static SelectQueryBuilder of(EntityMeta entityMeta) {
        return new SelectQueryBuilder(entityMeta);
    }

    public String buildSelectAllQuery() {
        return new StringBuilder()
                .append(getSelectHeaderQuery())
                .append(";")
                .toString();
    }

    public String buildSelectByPkQuery(Object pkObject) {
        return new StringBuilder()
                .append(getSelectHeaderQuery())
                .append(whereClauseBuilder.buildPkClause(pkObject))
                .append(";")
                .toString();
    }

    private String getSelectHeaderQuery() {
        ColumnMetas columnMetas = entityMeta.getColumnMetas();
        ColumnMetas exceptTransient = columnMetas.exceptTransient();
        return new StringBuilder()
                .append(SELECT)
                .append(exceptTransient.getColumnsClause())
                .append(FROM)
                .append(entityMeta.getTableName())
                .toString();
    }

}
