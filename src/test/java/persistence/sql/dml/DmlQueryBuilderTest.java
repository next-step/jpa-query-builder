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

        assertThat(insertDml).isEqualToIgnoringNewLines("insert into users (nick_name, old, email) values ('test', 20, 'test@email.com');");
    }

    @DisplayName("클래스 정보를 받아 select 쿼리를 생성한다")
    @Test
    void buildSelectAllQuery() {
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();

        String selectDml = dmlQueryBuilder.buildSelectAllQuery(InsertPerson.class);

        assertThat(selectDml).isEqualToIgnoringNewLines("select id, nick_name, old, email from users;");
    }

    @DisplayName("클래스 정보와 id를 받아 select 쿼리를 생성한다")
    @Test
    void buildSelectAll() {
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();

        String selectDml = dmlQueryBuilder.buildSelectByIdQuery(InsertPerson.class, 1L);

        assertThat(selectDml).isEqualToIgnoringNewLines("select id, nick_name, old, email from users where id = 1;");
    }

    @DisplayName("클래스 정보와 id를 받아 delete 쿼리를 생성한다")
    @Test
    void buildDeleteQuery() {
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();

        String deleteDml = dmlQueryBuilder.buildDeleteQuery(new InsertPerson(1L, "test", 20, "test@email.com", 1));

        assertThat(deleteDml).isEqualToIgnoringNewLines("delete from users where id = 1;");
    }
}
