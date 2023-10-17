package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryBuilderTest {

    @DisplayName("Person 객체를 통해서 drop 쿼리 생성")
    @Test
    void build() {
        //given
        Person changgunyee = new Person(1L, "changgunyee", 30, "minyoung403@naver.com");

        //when
        DropQueryBuilder builder = new DropQueryBuilder();
        String query = builder.build(changgunyee);

        //then
        assertThat(query).isEqualTo("DROP TABLE users;");
    }
}