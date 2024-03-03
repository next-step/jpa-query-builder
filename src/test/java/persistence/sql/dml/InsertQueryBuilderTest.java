package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.notcolumn.Person;
import persistence.sql.ddl.column.ColumnClauses;
import persistence.sql.dml.InsertQueryBuilder;

import java.lang.reflect.Field;
import java.util.List;

import static persistence.sql.dml.TestFixture.person_철수;

class InsertQueryBuilderTest {
    @Test
    @DisplayName("[요구사항 1.1] 컬럼 전체에 대해 insert 쿼리를 구현하라")
    void 요구사항1_1_test() {
        //given
        String expectedQuery = "INSERT INTO users (nick_name,old,email) VALUES ('김철수',21,'chulsoo.kim@gmail.com')";
        // when
        String actualQuery = new InsertQueryBuilder(Person.class).getInsertQuery(person_철수);

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 1.2] 컬럼 일부 대해 insert 쿼리를 구현하라")
    void 요구사항1_2_test() {
        //given
        String expectedQuery = "INSERT INTO users (nick_name,old,email) VALUES ('김철수',21,null)";
        // when
        String actualQuery = new InsertQueryBuilder(Person.class).getInsertQuery(List.of("name", "age"), List.of("김철수", 21));

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}