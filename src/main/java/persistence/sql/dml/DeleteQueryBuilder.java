package persistence.sql.dml;

import persistence.sql.dialect.Dialect;
import persistence.sql.dml.conditions.WhereRecord;
import persistence.sql.metadata.EntityMetadata;

import java.util.List;
import java.util.Objects;


public class DeleteQueryBuilder {

    public static final String DELETE_TEMPLATE = "DELETE FROM %s";
    private final Dialect dialect;
    private final EntityMetadata entity;
    private final WhereQueryBuilder whereQueryBuilder;

    private DeleteQueryBuilder(Dialect dialect, EntityMetadata entity, WhereQueryBuilder whereQueryBuilder) {
        this.dialect = dialect;
        this.entity = entity;
        this.whereQueryBuilder = whereQueryBuilder;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Dialect dialect;
        private EntityMetadata entity;
        private WhereQueryBuilder whereQueryBuilder;

        private Builder() {
        }

        public Builder dialect(Dialect dialect) {
            this.dialect = dialect;
            return this;
        }

        public Builder entity(Class<?> clazz) {
            this.entity = EntityMetadata.of(clazz);
            return this;
        }

        public Builder where(List<WhereRecord> whereRecords) {
            if (Objects.isNull(entity)) {
                throw new IllegalStateException("Entity must be set before setting where clause");
            }

            this.whereQueryBuilder = new WhereQueryBuilder(entity.getColumns(), whereRecords);
            return this;
        }

        public DeleteQueryBuilder build() {
            return new DeleteQueryBuilder(dialect, entity, whereQueryBuilder);
        }
    }

    public String generateQuery() {
        return String.format(DELETE_TEMPLATE, Objects.isNull(whereQueryBuilder) ? entity.getName() : String.join(" ", entity.getName(), whereQueryBuilder.generateWhereClausesQuery()));
    }
}
