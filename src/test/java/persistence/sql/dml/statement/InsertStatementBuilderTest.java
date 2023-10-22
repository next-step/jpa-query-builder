package persistence.sql.dml.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.fixture.PersonV3;
import persistence.sql.dialect.H2ColumnType;

@DisplayName("INSERT 문 생성 테스트")
class InsertStatementBuilderTest {

    @Test
    @DisplayName("Entity Class의 object에 대해 INSERT 구문을 생성할 수 있다.")
    void canInsertStatementByEntityObject() {
        InsertStatementBuilder insertStatementBuilder = new InsertStatementBuilder(new H2ColumnType());

        PersonV3 personV3 = new PersonV3("test_person", 25, "test@test.com", 5);
        final String insert = insertStatementBuilder.insert(personV3);

        assertThat(insert).isEqualTo("INSERT INTO USERS (nick_name, old, email) values ('test_person', 25, 'test@test.com')");
    }
}