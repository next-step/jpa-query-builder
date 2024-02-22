package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaseConverterTest {

    @Test
    @DisplayName("파스칼 케이스를 스네이크 케이스로 변환하기")
    void pascalToSnake() {
        String result = CaseConverter.pascalToSnake("CaseConverter");

        assertThat(result).isEqualTo("case_converter");
    }


    @Test
    @DisplayName("파스칼 케이스를 카멜 케이스로 변경하기")
    void pascalToCamel() {
        String result = CaseConverter.pascalToCamel("CaseConverter");

        assertThat(result).isEqualTo("caseConverter");
    }

    @Test
    @DisplayName("카멜 케이스를 스네이크 케이스로 변경하기")
    void camelToSnake() {
        String result = CaseConverter.camelToSnake("caseConverter");

        assertThat(result).isEqualTo("case_converter");
    }
}
