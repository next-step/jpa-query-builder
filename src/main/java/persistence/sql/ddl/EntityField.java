package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class EntityField {
    private String name;

    private boolean nullable;

    private EntityField(String name, boolean nullable) {
        this.name = name;
        this.nullable = nullable;
    }
}
