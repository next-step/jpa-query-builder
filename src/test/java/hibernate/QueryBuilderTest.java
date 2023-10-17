package hibernate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    private QueryBuilder queryBuilder = new QueryBuilder();

    @Test
    void Entity가_걸린_클래스의_create_쿼리를_생성한다() {
        // given
        String expected = ("create table person (" +
                "id BIGINT," +
                "age INTEGER," +
                "name VARCHAR" +
                ");").toLowerCase();

        // when
        String actual = queryBuilder.generateCreateQueries()
                .toLowerCase();

        // then
        assertThat(actual).isEqualToIgnoringWhitespace(expected);
    }
}
