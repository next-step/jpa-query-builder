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
        final EntityWithId entityWithId = new EntityWithId(1L, "Jaden", 30, "test@email.com");
        final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(entityWithId);

        // when
        final String query = deleteQueryBuilder.delete();

        // then
        assertThat(query).isEqualTo("DELETE FROM users WHERE id = 1");
    }
}
