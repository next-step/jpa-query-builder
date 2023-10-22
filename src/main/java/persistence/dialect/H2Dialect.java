package persistence.dialect;

public class H2Dialect extends Dialect {

    public H2Dialect() {
    }
    @Override
    public int javaTypeToJdbcType(String javaType) {
        return super.javaTypeToJdbcType(javaType);
    }

    @Override
    public String castType(int sqlType) {
        return super.castType(sqlType);
    }
}
