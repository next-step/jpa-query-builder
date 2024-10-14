package persistence.sql.dml.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.fixture.PersonV3;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UpdateQueryBuilder 테스트")
class UpdateQueryBuilderTest {
    private final UpdateQueryBuilder builder = new UpdateQueryBuilder(CamelToSnakeConverter.getInstance());
    private final MetadataLoader loader = new SimpleMetadataLoader<>(PersonV3.class);

    @Test
    @DisplayName("build 함수는 UPDATE 쿼리를 생성한다.")
    void testUpdateQueryBuild() {
        // given
        PersonV3 person = new PersonV3(1L, "catsbi", 55, "catsbi@naver.com", 123);

        // when
        String query = builder.build(loader, person);

        // then
        assertThat(query).isEqualTo("UPDATE users SET nick_name = 'catsbi', old = 55, email = 'catsbi@naver.com' WHERE id = 1");
    }
}