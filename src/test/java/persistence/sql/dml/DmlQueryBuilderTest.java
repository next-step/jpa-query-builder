package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.InsertPerson;

import static org.assertj.core.api.Assertions.assertThat;

class DmlQueryBuilderTest {

    @DisplayName("객체를 이용하여 insert 쿼리를 생성한다")
    @Test
    void buildInsertQuery() {
        InsertPerson insertPerson = new InsertPerson(
                1L,
                "test",
                20,
                "test@email.com",
                1
        );
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();

        String insertDml = dmlQueryBuilder.buildInsertQuery(insertPerson);

        assertThat(insertDml).isEqualTo("insert into users (nick_name, old, email) values ('test', 20, 'test@email.com');");
    }

}
