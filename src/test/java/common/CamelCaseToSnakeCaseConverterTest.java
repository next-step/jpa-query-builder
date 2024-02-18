package common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CamelCaseToSnakeCaseConverterTest {

    @ParameterizedTest(name = "convertToSnakeCase({0}) = {1}")
    @CsvSource(
        value = {
            "adminId,admin_id",
            "id,id",
            "camelCaseToSnakeCaseConverterTest,camel_case_to_snake_case_converter_test"
        },
        delimiter = ',')
    void convertToSnakeCase(String inputCamelCase, String expectedSnakeCase) {
        String actualSnakeCase = CamelCaseToSnakeCaseConverter.convertToSnakeCase(inputCamelCase);

        assertEquals(expectedSnakeCase, actualSnakeCase);
    }
}
