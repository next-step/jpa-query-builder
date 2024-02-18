package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum DataType {
    BIGINT(List.of(Long.class), "bigint"),
    INTEGER(List.of(Integer.class), "integer"),
    VARCHAR(List.of(String.class), "varchar(255)");

    private List<Object> types;
    private String printTypeName;

    DataType() {
    }

    DataType(final List<Object> classes, final String printTypeName) {
        this.types = classes;
        this.printTypeName = printTypeName;
    }

    public static String ofPrintType(final Field f) {
        return Arrays.stream(values())
                .filter(d -> d.types.contains(f.getType()))
                .map(d -> d.printTypeName)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
