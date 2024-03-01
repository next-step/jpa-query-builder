package domain.dialect;

import java.sql.Types;

/**
 * H2 라는 방언을 결정하는 곳
 */
public class H2Dialect extends Dialect {

    public H2Dialect() {
        registerColumnType(Types.INTEGER, "int");
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.VARCHAR, "varchar");
    }

    @Override
    public String getColumnDefine(int typeCode) {
        return super.getColumnDefine(typeCode);
    }
}
