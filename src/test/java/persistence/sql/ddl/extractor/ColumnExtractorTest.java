package persistence.sql.ddl.extractor;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.ddl.extractor.exception.ColumExtractorCreateException;
import persistence.sql.ddl.extractor.exception.GenerationTypeMissingException;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Entity
class TestClass {
    @Id
    private int id;

    @Transient
    private String ignore;

    @GeneratedValue(strategy = IDENTITY)
    private Long generated;

    private Long notGenerated;

    private Long nullable;
    @Column(nullable = false)
    private Long notNullable;

}

class ColumnExtractorTest {
    Dialect dialect = new H2Dialect();
    private static final String GENERATED_VALUE_FIELD_NAME = "generated";
    private static final String NOT_GENERATED_VALUE_FIELD_NAME = "notGenerated";

    @Test
    @DisplayName("Transient 필드로 생성자 직접호출시 에러")
    void errorWhenHasTransientField() {
        assertThrows(ColumExtractorCreateException.class, () -> {
            new ColumnExtractor(TestClass.class.getDeclaredField("ignore"));
        });
    }

    @ParameterizedTest()
    @CsvSource({
            GENERATED_VALUE_FIELD_NAME + ", true",
            NOT_GENERATED_VALUE_FIELD_NAME + ", false"
    })
    @DisplayName("hasGenerationType 테스트")
    void testHasGenerationType(String fieldName, boolean expected) throws Exception {
        ColumnExtractor columnExtractor =
                new ColumnExtractor(TestClass.class.getDeclaredField(fieldName));

        assertThat(columnExtractor.hasGenerationType()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GeneratedValue 아닌데 getGenerationType 호출시 에러")
    void errorWhenGetGenerationTypeInvokedButIsNotGeneratedValue() throws Exception {
        ColumnExtractor columnExtractor =
                new ColumnExtractor(TestClass.class.getDeclaredField(NOT_GENERATED_VALUE_FIELD_NAME));

        assertThrows(GenerationTypeMissingException.class, columnExtractor::getGenerationType);
    }

    @Test
    @DisplayName("getGenerationType 테스트")
    void testGetGenerationType() throws Exception {
        ColumnExtractor columnExtractor =
                new ColumnExtractor(TestClass.class.getDeclaredField(GENERATED_VALUE_FIELD_NAME));
        assertThat(columnExtractor.getGenerationType()).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({
            "nullable, true",
            "notNullable, false"
    })
    @DisplayName("isNullable 테스트")
    void testIsNullable(String fieldName, boolean expected) throws Exception {
        ColumnExtractor columnExtractor =
                new ColumnExtractor(TestClass.class.getDeclaredField(fieldName));

        assertThat(columnExtractor.isNullable()).isEqualTo(expected);
    }
}
