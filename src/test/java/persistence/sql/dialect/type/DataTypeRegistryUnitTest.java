package persistence.sql.dialect.type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.model.meta.DataType;

import java.util.List;
import java.util.NoSuchElementException;

import static java.sql.Types.*;
import static org.junit.jupiter.api.Assertions.*;

public class DataTypeRegistryUnitTest {
    private DataTypeRegistry dataTypeRegistry;

    @BeforeEach
    void setUp() {
        dataTypeRegistry = new DataTypeRegistry(new TestDataTypeMappingStrategy());
    }

    @Test
    @DisplayName("등록된 데이터타입 객체를 정상적으로 불러온다.")
    void testGetDataType() {
        DataType stringDataType = dataTypeRegistry.getDataType(String.class);

        assertAll(
                () -> assertEquals("varchar(%d)", stringDataType.namePattern()),
                () -> assertEquals(VARCHAR, stringDataType.sqlTypeCode())
        );
    }

    @Test
    @DisplayName("등록하지 않은 데이터타입을 불러오면 에러가 발생한다.")
    void testGetDataTypeThrowsExceptionForUnsupportedJavaType() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            dataTypeRegistry.getDataType(Double.class);
        });
        assertEquals("UNSUPPORTED JAVA TYPE : Double", exception.getMessage());
    }

    private static class TestDataTypeMappingStrategy implements DataTypeMappingStrategy {
        @Override
        public List<Integer> getMappingSqlCodes() {
            return List.of(VARCHAR);
        }

        @Override
        public Class<?> mapSqlCodeToJavaType(int typeCode) {
            return switch (typeCode) {
                case VARCHAR -> String.class;
                default -> throw new IllegalArgumentException("UNKNOWN TYPE. sql code = " + typeCode);
            };
        }

        @Override
        public String mapSqlCodeToNamePattern(int typeCode) {
            return switch (typeCode) {
                case VARCHAR -> "varchar(%d)";
                default -> throw new IllegalArgumentException("UNKNOWN TYPE. sql code = " + typeCode);
            };
        }
    }
}
