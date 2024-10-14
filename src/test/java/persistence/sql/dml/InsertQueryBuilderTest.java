package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fixture.EntityWithId;

import static org.assertj.core.api.Assertions.*;

class InsertQueryBuilderTest {
    @Test
    @DisplayName("insert 쿼리를 생성한다.")
    void insert() {
        // given
        final EntityWithId entityWithId = new EntityWithId("Jaden", 30, "test@email.com", 1);
        final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entityWithId);

        // when
        final String query = insertQueryBuilder.insert();

        // then
        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('Jaden', 30, 'test@email.com')");
    }
}
