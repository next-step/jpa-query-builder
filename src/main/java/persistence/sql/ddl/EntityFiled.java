package persistence.sql.ddl;

import java.sql.JDBCType;

public class EntityFiled {
    private final String name;
    private final JDBCType jdbcType;
    public EntityFiled(String name, JDBCType jdbcType) {
        this.name = name;
        this.jdbcType = jdbcType;
    }
    public String generateFiledDDL() {
        StringBuilder builder = new StringBuilder(name)
                .append(" ")
                .append(jdbcType.getName().toLowerCase());

        if (jdbcType.equals(JDBCType.VARCHAR)) {
            builder.append("(255)");
        }

        return builder.toString();
    }

}
