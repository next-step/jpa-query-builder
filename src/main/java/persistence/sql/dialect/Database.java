package persistence.sql.dialect;

public enum Database {

    MYSQL {
        @Override
        public Dialect createDialect() {
            return new MysqlDialect();
        }
    },
    ;

    public abstract Dialect createDialect();
}
