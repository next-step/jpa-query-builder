package persistence.sql.dml;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    @DisplayName("select all 쿼리 생성 확인")
    @Test
    void findAll() {
        //when
        String selectAllQuery = new SelectQueryBuilder()
                .findAll(Person.class);

        //then
        assertThat(selectAllQuery)
                .isEqualTo("SELECT * FROM users;");

    }

    @DisplayName("select by id 쿼리 생성 확인")
    @Test
    void findById() {
        //when
        String findById = new SelectQueryBuilder()
                .findById(Person.class, Arrays.asList("1"));

        //then
        assertThat(findById)
                .isEqualTo("SELECT * FROM users WHERE id=1");
    }
}