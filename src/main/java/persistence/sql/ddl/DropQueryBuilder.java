package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.metadata.EntityMetadata;

public class DropQueryBuilder {

    public static final String DROP_TABLE_TEMPLATE = "DROP TABLE %s";
    private final Dialect dialect;
    private final EntityMetadata entity;

    private DropQueryBuilder(Dialect dialect, EntityMetadata entity) {
        this.dialect = dialect;
        this.entity = entity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Dialect dialect;
        private EntityMetadata entity;

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

        public DropQueryBuilder build() {
            return new DropQueryBuilder(dialect, entity);
        }
    }

    public String generateQuery() {
        return String.format(DROP_TABLE_TEMPLATE, entity.getName());
    }
}
