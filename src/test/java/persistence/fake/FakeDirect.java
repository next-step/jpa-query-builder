package persistence.fake;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.H2Direct;

public class FakeDirect extends H2Direct {
    @Override
    public String getBigInt() {
        return super.getBigInt().toUpperCase();
    }

    @Override
    public String getVarchar() {
        return super.getVarchar().toUpperCase();
    }

    @Override
    public String getInteger() {
        return super.getInteger().toUpperCase();
    }

    @Override
    public String getGeneratedType(GenerationType generationType) {
        return super.getGeneratedType(generationType).toUpperCase();
    }

    @Override
    public String notNull() {
        return super.notNull().toUpperCase();
    }

    @Override
    public String primaryKey(String columnName) {
        return super.primaryKey(columnName).toUpperCase();
    }
}
