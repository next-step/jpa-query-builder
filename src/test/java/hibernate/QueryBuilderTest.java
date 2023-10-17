package hibernate;

import domain.Person;
import domain.Person2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryBuilderTest {

    private QueryBuilder queryBuilder = new QueryBuilder();

    @Test
    void Entity가_걸린_클래스의_create_쿼리를_생성한다() {
        // given
        Pattern expectedPattern = Pattern.compile("create table person \\(([^)]+(?:,\\s*[^)]+)*)\\);");
        List<String> expectedColumns = List.of("id bigint", "age integer", "name varchar");

        // when
        String actual = queryBuilder.generateCreateQueries(Person.class)
                .toLowerCase();

        // then
        assertAll(
                () -> assertThat(actual).matches(expectedPattern),
                () -> assertThat(actual).contains(expectedColumns)
        );
    }

    @Test
    void Id가_걸린_필드는_기본키로_create_쿼리를_생성한다() {
        // given
        String expectedColumn = "id bigint primary key";

        // when
        String actual = queryBuilder.generateCreateQueries(Person.class);

        // then
        assertThat(actual).contains(expectedColumn);
    }

    @Test
    void Column이_걸린_필드는_컬럼명이_해당값으로_create_쿼리가_생성된다() {
        // given
        List<String> expectedColumns = List.of("nick_name varchar", "old integer", "email varchar");

        // when
        String actual = queryBuilder.generateCreateQueries(Person2.class)
                .toLowerCase();

        // then
        assertThat(actual).contains(expectedColumns);
    }
}
