package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DmlQueryBuilderTest {

    @Test
    void insertSql() {
        final Person 정원 = new Person(
                null,
                "정원",
                15,
                "a@a.com",
                1
        );

        final String insertSql = new DmlQueryBuilder(Person.class)
                .insert(정원);

        assertThat(insertSql).isEqualTo("insert into users (nick_name,old,email) values ('정원',15,'a@a.com');");
    }

    @Test
    void findAllSql() {
        final String findAllSql = new DmlQueryBuilder(Person.class).findAll();

        assertThat(findAllSql).isEqualTo("select id,nick_name,old,email from users;");
    }

}
