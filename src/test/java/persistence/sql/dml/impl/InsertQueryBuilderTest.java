package persistence.sql.dml.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.fixture.PersonV3;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InsertQueryBuilder 테스트")
class InsertQueryBuilderTest {
    private final InsertQueryBuilder builder = new InsertQueryBuilder(CamelToSnakeConverter.getInstance());
    private final MetadataLoader loader = new SimpleMetadataLoader<>(PersonV3.class);

    @Test
    @DisplayName("build 함수는 INSERT 쿼리를 생성한다.")
    void testInsertQueryBuild() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "catsbi@naver.com", 123);

        // when
        String query = builder.build(loader, person);

        // then
        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('catsbi', 55, 'catsbi@naver.com')");

    }
}