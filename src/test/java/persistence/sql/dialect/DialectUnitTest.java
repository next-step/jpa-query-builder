package persistence.sql.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        String intType = dialect.getDataTypeFullName(Integer.class, -1);
        String varcharType = dialect.getDataTypeFullName(String.class, 500);
        String bigIntType = dialect.getDataTypeFullName(Long.class, -1);

        assertAll(
                () -> assertEquals("int", intType),
                () -> assertEquals("varchar(500)", varcharType),
                () -> assertEquals("bigint", bigIntType)
        );
    }

    @Test
    void testGetExtendedDataType() {
        String dataType = dialect.getDataTypeFullName(LocalDateTime.class, 255);

        assertEquals("DATETIME", dataType);
    }

    @Test
    @DisplayName("등록되지 않은 타입을 조회하려 하면 에러가 발생한다.")
    void testGetDataTypeThrowsExceptionForUnsupportedType() {
        assertThrows(NoSuchElementException.class, () -> {
            dialect.getDataTypeFullName(Double.class, 255);
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
