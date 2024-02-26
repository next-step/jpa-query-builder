package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.notcolumn.Person;
import persistence.sql.dml.InsertQueryBuilder;

class InsertQueryBuilderTest {
    @Test
    @DisplayName("[요구사항 1] insert 쿼리를 구현하라")
    void 요구사항1_test() {
        //given
        String expectedQuery = "INSERT INTO users (id,nick_name,old,email) VALUES (김철수,21,chulsoo.kim@gmail.com)";
        // when
        String actualQuery = new InsertQueryBuilder(Person.class)
                .getInsertQuery(new Person("김철수", 21, "chulsoo.kim@gmail.com", 11));

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}