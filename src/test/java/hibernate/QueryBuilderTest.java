package hibernate;

import domain.Person;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    private QueryBuilder queryBuilder = new QueryBuilder();

    @Test
    void Entity가_걸린_클래스의_create_쿼리를_생성한다() {
        // given
        Pattern expectedPattern = Pattern.compile("create table person \\(([^)]+(?:,\\s*[^)]+)*)\\);");
        List<String> expectedColumn = List.of("id bigint", "age integer", "name varchar");

        // when
        String actual = queryBuilder.generateCreateQueries(Person.class)
                .toLowerCase();

        // then
        assertThat(actual).matches(expectedPattern);
        assertThat(actual).contains(expectedColumn);
    }
}
