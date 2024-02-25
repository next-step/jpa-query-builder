package database.sql.util.column;

import database.sql.util.type.MySQLTypeConverter;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class GeneralEntityColumnTest {
    private final MySQLTypeConverter typeConverter = new MySQLTypeConverter();

    private static List<Arguments> sampleGeneralColumns() {
        return List.of(
//                arguments(new GeneralEntityColumn("abc", Long.class, null, false), "abc BIGINT NOT NULL"),
//                arguments(new GeneralEntityColumn("email", String.class, 42, true), "email VARCHAR(42) NULL"),
//                arguments(new GeneralEntityColumn("old", Integer.class, 42, false), "old INT NOT NULL")
        );
    }

//    @ParameterizedTest
//    @MethodSource("sampleGeneralColumns")
//    void toColumnDefinition(EntityColumn entityColumn, String expected) {
//        String columnDefinition = entityColumn.toColumnDefinition(typeConverter);
//        assertThat(columnDefinition).isEqualTo(expected);
//    }
//
//    @ParameterizedTest
//    @MethodSource("sampleGeneralColumns")
//    void isPrimaryKeyColumn(EntityColumn entityColumn) {
//        assertThat(entityColumn.isPrimaryKeyField()).isFalse();
//    }
}
