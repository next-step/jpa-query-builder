package util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaseConverterTest {

    @Test
    void pascalToSnake() {
        String result = CaseConverter.pascalToSnake("CaseConverter");

        assertThat(result).isEqualTo("case_converter");
    }


    @Test
    void pascalToCamel() {
        String result = CaseConverter.pascalToCamel("CaseConverter");

        assertThat(result).isEqualTo("caseConverter");
    }
}
