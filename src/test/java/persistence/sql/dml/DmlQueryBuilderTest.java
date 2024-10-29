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
        DmlQueryBuilder<InsertPerson> dmlQueryBuilder = DmlQueryBuilder.from(InsertPerson.class);

        String insertDml = dmlQueryBuilder.buildInsertQuery(insertPerson);

        assertThat(insertDml).isEqualTo("insert into users (nick_name, old, email) values ('test', 20, 'test@email.com');");
    }

    @DisplayName("클래스 정보를 받아 select 쿼리를 생성한다")
    @Test
    void buildSelectByIdQuery() {
        DmlQueryBuilder<InsertPerson> dmlQueryBuilder = DmlQueryBuilder.from(InsertPerson.class);

        String selectDml = dmlQueryBuilder.buildSelectByIdQuery();

        assertThat(selectDml).isEqualTo("select * from users;");
    }

    @DisplayName("클래스 정보와 id를 받아 select 쿼리를 생성한다")
    @Test
    void buildSelectByIdQueryWithId() {
        DmlQueryBuilder<InsertPerson> dmlQueryBuilder = DmlQueryBuilder.from(InsertPerson.class);

        String selectDml = dmlQueryBuilder.buildSelectByIdQuery(1L);

        assertThat(selectDml).isEqualTo("select * from users where id = 1;");
    }

    @DisplayName("클래스 정보와 id를 받아 delete 쿼리를 생성한다")
    @Test
    void buildDeleteByIdQuery() {
        DmlQueryBuilder<InsertPerson> dmlQueryBuilder = DmlQueryBuilder.from(InsertPerson.class);

        String deleteDml = dmlQueryBuilder.buildDeleteByIdQuery(1L);

        assertThat(deleteDml).isEqualTo("delete from users where id = 1;");
    }
}
