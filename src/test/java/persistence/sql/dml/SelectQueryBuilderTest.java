package persistence.sql.dml;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}