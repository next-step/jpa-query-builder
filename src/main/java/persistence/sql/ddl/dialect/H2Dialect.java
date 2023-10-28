package persistence.sql.ddl.dialect;

import persistence.sql.ddl.scheme.ColumnSchemes;
import persistence.sql.ddl.scheme.Schemes;

import java.sql.Types;

public class H2Dialect extends Dialect {

    public H2Dialect() {
        registerColumnType(Types.VARCHAR, "VARCHAR");
        registerColumnType(Types.INTEGER, "INT");
        registerColumnType(Types.BIGINT, "BIGINT");
        registerColumnScheme(Schemes.Column, ColumnSchemes.builder().addColumnScheme("nullable", "NOT NULL").build());
        registerColumnScheme(Schemes.GeneratedValue, ColumnSchemes.builder().addColumnScheme("IDENTITY", "AUTO_INCREMENT").build());
        registerColumnScheme(Schemes.Id, ColumnSchemes.builder().addColumnScheme("Id", "PRIMARY KEY").build());
    }

}
