package persistence.sql.clause;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("WhereConditionalClause 테스트")
class WhereConditionalClauseTest {

    @Test
    @DisplayName("WhereConditionalClause 레코드 클래스를 생성한다.")
    void create() {
        WhereConditionalClause actual = new WhereConditionalClause("name", "catsbi", "=");

        assertThat(actual.column()).isEqualTo("name");
        assertThat(actual.value()).isEqualTo("catsbi");
        assertThat(actual.operator()).isEqualTo("=");
        assertThat(actual.clause()).isEqualTo("name = catsbi");
    }

    @ParameterizedTest
    @MethodSource("provideWhereConditionalClause")
    @DisplayName("WhereConditionalClause 레코드 클래스를 생성시 하나라도 값이 없을 경우 예외를 던진다..")
    void createWithNull(String column, String value, String operator) {
        assertThatThrownBy(() -> new WhereConditionalClause(column, value, operator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Column, value, operator must not be null");
    }

    public static Stream<Arguments> provideWhereConditionalClause() {
        return Stream.of(
                Arguments.of(null, "catsbi", "="),
                Arguments.of("name", null, "="),
                Arguments.of("name", "catsbi", null)
        );
    }

    @Test
    @DisplayName("eq 빌더 표현식을 이용해 eqluals 조건을 생성한다.")
    void eqTest() {
        WhereConditionalClause actual = WhereConditionalClause.builder().column("name").eq("catsbi");

        assertThat(actual.column()).isEqualTo("name");
        assertThat(actual.value()).isEqualTo("catsbi");
        assertThat(actual.operator()).isEqualTo("=");
        assertThat(actual.clause()).isEqualTo("name = catsbi");
    }

    @Test
    @DisplayName("in 빌더 표현식을 이용해 in 조건을 생성한다.")
    void inTest() {
        WhereConditionalClause actual = WhereConditionalClause.builder().column("name").in(List.of("catsbi", "crong"));

        assertThat(actual.column()).isEqualTo("name");
        assertThat(actual.value()).isEqualTo("'catsbi' , 'crong'");
        assertThat(actual.operator()).isEqualTo("IN");
        assertThat(actual.clause()).isEqualTo("name IN 'catsbi' , 'crong'");
    }

    @Test
    @DisplayName("neq 빌더 표현식을 이용해 not equals 조건을 생성한다.")
    void neqTest() {
        WhereConditionalClause actual = WhereConditionalClause.builder().column("name").neq("catsbi");

        assertThat(actual.column()).isEqualTo("name");
        assertThat(actual.value()).isEqualTo("catsbi");
        assertThat(actual.operator()).isEqualTo("!=");
        assertThat(actual.clause()).isEqualTo("name != catsbi");
    }

    @Test
    @DisplayName("notIn 빌더 표현식을 이용해 not in 조건을 생성한다.")
    void notInTest() {
        WhereConditionalClause actual = WhereConditionalClause.builder().column("name").notIn(List.of("catsbi", "crong"));

        assertThat(actual.column()).isEqualTo("name");
        assertThat(actual.value()).isEqualTo("'catsbi' , 'crong'");
        assertThat(actual.operator()).isEqualTo("NOT IN");
        assertThat(actual.clause()).isEqualTo("name NOT IN 'catsbi' , 'crong'");
    }
}