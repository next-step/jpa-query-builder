package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class SelectAllQueryBuilderTest {

    @DisplayName("Person 객체를 select all 쿼리로 변환한다.")
    @Test
    void testSelectAllDml() {
        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.generate(Person.class, Database.MYSQL);
        String selectAll = selectQueryBuilder.build().findAll();

        assertThat("select id, nick_name, old, email from users").isEqualTo(selectAll);
    }


}
