package persistence.sql.dml.statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.fixture.PersonV3;
import persistence.sql.dialect.H2ColumnType;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.operator.EqualOperator;

@DisplayName("SELECT 문 생성 테스트")
class SelectStatementBuilderTest {


    @Test
    @DisplayName("Select 문을 생성할 수 있다.")
    void canBuildSelectStatement() {
        final String selectStatement = SelectStatementBuilder.builder()
            .select(PersonV3.class, new H2ColumnType())
            .build();

        assertThat(selectStatement).isEqualTo("SELECT * FROM USERS;");
    }

    @Test
    @DisplayName("원하는 필드만 갖고 오는 Select 문을 생성할 수 있다.")
    void canBuildSelectStatementSpecificField() {
        final String selectStatement = SelectStatementBuilder.builder()
            .select(PersonV3.class, new H2ColumnType(), "id")
            .build();

        assertThat(selectStatement).isEqualTo("SELECT id FROM USERS;");
    }

    @Test
    @DisplayName("정의되지 않은 필드를 갖고 오는 Select 문을 생성할 수 없다.")
    void cannotBuildSelectStatementUndefinedField() {
        assertThatThrownBy(() -> {
            SelectStatementBuilder.builder()
                .select(PersonV3.class, new H2ColumnType(), "phone_number")
                .build();
        }).isInstanceOf(RuntimeException.class)
            .hasMessage(String.format("%s 필드는 정의되지 않은 필드입니다.", "[phone_number]"));
    }

    @Test
    @DisplayName("Where절을 통해 조건이 있는 Select 문을 생성할 수 있다.")
    void canBuildSelectStatementWhere() {
        final String selectStatement = SelectStatementBuilder.builder()
            .select(PersonV3.class, new H2ColumnType())
            .where(WherePredicate.of("id", 1L, new EqualOperator()))
            .build();

        assertThat(selectStatement).isEqualTo("SELECT * FROM USERS WHERE id = 1;");
    }

    @Test
    @DisplayName("Where절에 AND 조건으로 Select 문을 생성할 수 있다.")
    void canBuildSelectStatementWhereWithAnd() {
        final String selectStatement = SelectStatementBuilder.builder()
            .select(PersonV3.class, new H2ColumnType())
            .where(WherePredicate.of("id", 1L, new EqualOperator()))
            .and(WherePredicate.of("nick_name", "test_person", new EqualOperator()))
            .build();

        assertThat(selectStatement).isEqualTo("SELECT * FROM USERS WHERE id = 1 AND nick_name = 'test_person';");
    }

    @Test
    @DisplayName("Where절에 OR 조건으로 Select 문을 생성할 수 있다.")
    void canBuildSelectStatementWhereWithOr() {
        final String selectStatement = SelectStatementBuilder.builder()
            .select(PersonV3.class, new H2ColumnType())
            .where(WherePredicate.of("id", 1L, new EqualOperator()))
            .or(WherePredicate.of("nick_name", "test_person", new EqualOperator()))
            .build();

        assertThat(selectStatement).isEqualTo("SELECT * FROM USERS WHERE id = 1 OR nick_name = 'test_person';");
    }
}