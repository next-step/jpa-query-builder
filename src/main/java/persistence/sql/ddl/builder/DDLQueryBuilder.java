package persistence.sql.ddl.builder;

import persistence.sql.ddl.DDLQueryValidator;
import persistence.sql.ddl.H2JavaToSqlMapper;
import persistence.sql.ddl.model.DDLType;
import persistence.sql.ddl.model.DatabaseType;

public abstract class DDLQueryBuilder {

    public static Builder build() {
        return new Builder();
    }

    abstract String prepareStatement(Class<?> tClass);

    public static class Builder {
        protected JavaToSqlMapper javaToSqlMapper;
        protected DDLQueryValidator validator = new DDLQueryValidator();
        protected JavaToSqlConverter javaToSqlConverter;
        private DatabaseType databaseType;
        private DDLType ddlType;

        public Builder ddlType(DDLType ddlType) {
            this.ddlType = ddlType;
            return this;
        }

        public Builder validator(DDLQueryValidator validator) {
            this.validator = validator;
            return this;
        }

        public Builder database(DatabaseType databaseType) {
            this.databaseType = databaseType;
            if (databaseType.equals(DatabaseType.H2)) {
                this.javaToSqlConverter = new JavaToSqlConverter(new H2JavaToSqlMapper());
                return this;
            }
            throw new IllegalArgumentException(String.format("[%s] 잘못된 데이터베이스 타입이 들어왔습니다.", databaseType));
        }

        public DDLQueryBuilder build() {
            if (ddlType.equals(DDLType.CREATE)) {
                return new CreateDDLQueryBuilder(this);
            }

            if (ddlType.equals(DDLType.DROP)) {
                return new DropDDLQueryBuilder(this);
            }
            throw new IllegalArgumentException(String.format("[%s] 잘못된 DDL type이 들어왔습니다.", ddlType));
        }
    }
}
