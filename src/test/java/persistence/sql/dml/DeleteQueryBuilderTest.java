package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fixture.EntityWithId;

import static org.assertj.core.api.Assertions.*;

class DeleteQueryBuilderTest {
    @Test
    @DisplayName("delete 쿼리를 생성한다.")
    void delete() {
        // given
        final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(EntityWithId.class);

        // when
        final String query = deleteQueryBuilder.delete(1);

        // then
        assertThat(query).isEqualTo("DELETE FROM users WHERE id = 1");
    }
}
