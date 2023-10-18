package persistence.fake;

import jakarta.persistence.GenerationType;
import persistence.vender.dialect.H2Dialect;

public class FakeDirect extends H2Dialect {
    @Override
    public String getBigInt() {
        return super.getBigInt().toUpperCase();
    }

    @Override
    public String getVarchar(int length) {
        return super.getVarchar(length).toUpperCase();
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

    @Override
    public String insert(String tableName) {
        return super.insert(tableName).toUpperCase();
    }
}
