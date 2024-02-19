package persistence.sql.ddl.domain;

import jakarta.persistence.GenerationType;

public class DatabasePrimaryColumn extends DatabaseColumn {

    private final GenerationType generationType;

    public DatabasePrimaryColumn(String name, Class<?> javaType, Integer size, GenerationType generationType) {
        super(name, javaType, size, false);
        this.generationType = generationType;
    }

    public boolean hasIdentityStrategy() {
        return generationType.equals(GenerationType.IDENTITY) || generationType.equals(GenerationType.AUTO);
    }
}
