package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    @DisplayName("Person 객체를 select one 쿼리로 변환한다.")
    @Test
    void buildFindQuery() {

        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.generate(Person.class, Database.MYSQL);
        String selectOneQuery = selectQueryBuilder.build().findById(1L);
        assertThat(selectOneQuery).isEqualTo("select id, nick_name, old, email from users where id = 1");

    }
}
