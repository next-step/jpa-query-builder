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
        dataTypeRegistry = new DataTypeRegistry() {
            @Override
            List<Integer> getMappingSqlCodes() {
                return List.of(INTEGER);
            }

            @Override
            Class<?> mapSqlCodeToJavaType(int typeCode) {
                if (typeCode == INTEGER) {
                    return Integer.class;
                }
                throw new IllegalArgumentException();
            }

            @Override
            String mapSqlCodeToNamePattern(int typeCode) {
                if (typeCode == INTEGER) {
                    return "int";
                }
                throw new IllegalArgumentException();
            }
        };
    }

    @Test
    @DisplayName("등록된 데이터타입 객체를 정상적으로 불러온다.")
    void testGetDataType() {
        DataType stringDataType = dataTypeRegistry.getDataType(Integer.class);

        assertAll(
                () -> assertEquals("int", stringDataType.namePattern()),
                () -> assertEquals(INTEGER, stringDataType.sqlTypeCode())
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
}
