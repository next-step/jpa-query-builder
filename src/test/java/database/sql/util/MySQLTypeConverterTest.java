package database.sql.util;

import database.sql.util.type.MySQLTypeConverter;
import database.sql.util.type.TypeConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLTypeConverterTest {

    @Test
    void convert() {
        assertConversion(Long.class, null, "BIGINT");
        assertConversion(String.class, 10, "VARCHAR(10)");
        assertConversion(String.class, 20, "VARCHAR(20)");
        assertConversion(Integer.class, null, "INT");
    }

    private void assertConversion(Class<?> entityType, Integer columnLength, String databaseType) {
        TypeConverter converter = new MySQLTypeConverter();

        assertThat(converter.convert(entityType, columnLength)).isEqualTo(databaseType);
    }
}
