package persistence.sql.dml.builder;

import persistence.sql.meta.EntityMeta;

public class DeleteQueryBuilder {

    private static final String DELETE = "DELETE";
    private static final String FROM = " FROM ";

    private final EntityMeta entityMeta;
    private final WhereClauseBuilder whereClauseBuilder;

    private DeleteQueryBuilder(EntityMeta entityMeta) {
        validateEntityAnnotation(entityMeta);
        this.entityMeta = entityMeta;
        this.whereClauseBuilder = WhereClauseBuilder.builder(entityMeta);
    }

    private void validateEntityAnnotation(EntityMeta entityMeta) {
        if (!entityMeta.isEntity()) {
            throw new IllegalArgumentException("Delete Query 빌드 대상이 아닙니다.");
        }
    }

    public static DeleteQueryBuilder of(EntityMeta entityMeta) {
        return new DeleteQueryBuilder(entityMeta);
    }

    public String buildDeleteAllQuery() {
        return new StringBuilder()
                .append(getDeleteHeaderQuery())
                .append(";")
                .toString();
    }

    public String buildDeleteByPkQuery(Object pkObject) {
        return new StringBuilder()
                .append(getDeleteHeaderQuery())
                .append(whereClauseBuilder.buildPkClause(pkObject))
                .append(";")
                .toString();
    }

    public String buildDeleteQuery(Object entity) {
        return new StringBuilder()
                .append(getDeleteHeaderQuery())
                .append(whereClauseBuilder.appendPkClause(entity).build())
                .append(";")
                .toString();
    }

    private String getDeleteHeaderQuery() {
        return new StringBuilder()
                .append(DELETE)
                .append(FROM)
                .append(entityMeta.getTableName())
                .toString();
    }
}
