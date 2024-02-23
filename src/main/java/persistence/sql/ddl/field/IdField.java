package persistence.sql.ddl.field;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class IdField implements QueryField {

    private final Field field;

    public IdField(Field field) {
        if (!isMappableField(field)) {
            throw new IllegalArgumentException("@Id가 존재하지 않습니다");
        }
        this.field = field;
    }

    public static boolean isMappableField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    @Override
    public String toSQL() {
        return String.join(" ",
                field.getName(),
                QueryDataType.from(field.getType()),
                "PRIMARY KEY",
                "AUTO_INCREMENT"
        );
    }
}
