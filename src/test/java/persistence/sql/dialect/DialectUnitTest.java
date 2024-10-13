package persistence.sql.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.model.meta.DataType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static java.sql.Types.*;
import static org.junit.jupiter.api.Assertions.*;

class DialectUnitTest {
    private Dialect dialect;

    private static class RandomDialect extends Dialect {
        @Override
        protected void initiateDataTypeRegistry() {
            super.initiateDataTypeRegistry();
            registerDataType(TIMESTAMP);
        }

        protected Class<?> mapSqlCodeToJavaType(int typeCode) {
            return switch (typeCode) {
                case TIMESTAMP -> LocalDateTime.class;
                default -> super.mapSqlCodeToJavaType(typeCode);
            };
        }

        protected String mapSqlCodeToNamePattern(int typeCode) {
            return switch (typeCode) {
                case TIMESTAMP -> "DATETIME";
                default -> super.mapSqlCodeToNamePattern(typeCode);
            };
        }
    }

    @BeforeEach
    void setUp() {
        dialect = new RandomDialect();
    }

    @Test
    @DisplayName("표준 타입을 가져온다.")
    void testGetCommonDataType() {
        DataType intType = dialect.getDataType(Integer.class);
        DataType varcharType = dialect.getDataType(String.class);
        DataType bigIntType = dialect.getDataType(Long.class);

        assertAll(
                () -> assertEquals(INTEGER, intType.sqlTypeCode()),
                () -> assertEquals(VARCHAR, varcharType.sqlTypeCode()),
                () -> assertEquals(BIGINT, bigIntType.sqlTypeCode())
        );
    }

    @Test
    void testGetExtendedDataType() {
        DataType dataType = dialect.getDataType(LocalDateTime.class);

        assertEquals(TIMESTAMP, dataType.sqlTypeCode());
    }

    @Test
    @DisplayName("등록되지 않은 타입을 조회하려 하면 에러가 발생한다.")
    void testGetDataTypeThrowsExceptionForUnsupportedType() {
        assertThrows(NoSuchElementException.class, () -> {
            dialect.getDataType(Double.class);
        });
    }

    @Test
    void testGetIdentifierQuoted() {
        String identifier = "testColumn";
        String quotedIdentifier = dialect.getIdentifierQuoted(identifier);

        assertEquals("\"testColumn\"", quotedIdentifier);
    }

    @Test
    void testGetDropTablePhrase() {
        String dropTablePhrase = dialect.getDropTablePhrase();

        assertEquals("DROP TABLE IF EXISTS", dropTablePhrase);
    }

    @Test
    void testBuildPrimaryKeyPhrase() {
        String primaryKeyPhrase = dialect.buildPrimaryKeyPhrase(List.of("id", "name"));

        assertEquals("PRIMARY KEY (\"id\", \"name\")", primaryKeyPhrase);
    }
}
