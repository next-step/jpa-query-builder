package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.querybuilder.QueryBuilder;

public class QueryBuilderTest {

    @Test
    @DisplayName("select 구문 생성")
    void selectTest() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String sql = queryBuilder
            .select("name, age")
            .from("person")
            .where("age > 20")
            .and("name = 'John'")
            .build();

        String expected = "SELECT name, age FROM person WHERE age > 20 AND name = 'John'";
        Assertions.assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("delete 구문 생성")
    void deleteTest() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String sql = queryBuilder
            .delete()
            .from("person")
            .where("age > 20")
                .and("name = 'John'")
            .build();

        String expected = "DELETE FROM person WHERE age > 20 AND name = 'John'";
        Assertions.assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("insert 구문 생성")
    void insertTest() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String sql = queryBuilder
            .insertInto("person")
            .columns("name, age")
            .values("John, 20")
            .build();

        System.out.println(sql);
        String expected = "INSERT INTO person (name, age) VALUES (John, 20)";
        Assertions.assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("update 구문 생성")
    void updateTest() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String sql = queryBuilder
            .update("person")
            .set("name", "'John'")
            .where("age > 20")
                .or("age < 30")
                .and("name = 'John'")
            .build();

        String expected = "UPDATE person SET name = 'John' WHERE age > 20 OR age < 30 AND name = 'John'";
        Assertions.assertThat(sql).isEqualTo(expected);
    }
}
