package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.QueryBuilderFactory;
import persistence.sql.data.QueryType;
import persistence.sql.dml.impl.SimpleMetadataLoader;
import persistence.sql.fixture.PersonV3;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderFactoryTest {
    private final QueryBuilderFactory factory = QueryBuilderFactory.getInstance();
    private final MetadataLoader loader = new SimpleMetadataLoader<>(PersonV3.class);


    @ParameterizedTest
    @DisplayName("buildQuery 함수는 매개변수 queryType에 따라 적절한 쿼리를 생성한다.")
    @CsvSource(value = {
            "SELECT:SELECT id, nick_name, old, email FROM users WHERE id = 1",
            "INSERT:INSERT INTO users (nick_name, old, email) VALUES ('catsbi', 55, 'catsbi@naver.com')",
            "UPDATE:UPDATE users SET nick_name = 'catsbi', old = 55, email = 'catsbi@naver.com' WHERE id = 1",
            "DELETE:DELETE FROM users WHERE id = 1"
    }, delimiter = ':')
    void testBuildQuery(String queryType, String expected) {
        // given
        PersonV3 person = new PersonV3(1L, "catsbi", 55, "catsbi@naver.com", 123);

        // when
        QueryType foundQueryType = QueryType.valueOf(queryType);
        String actual = factory.buildQuery(foundQueryType, loader, person);

        assertThat(actual).isEqualTo(expected);
    }

}