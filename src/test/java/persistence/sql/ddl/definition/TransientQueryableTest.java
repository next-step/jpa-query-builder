package persistence.sql.ddl.definition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Queryable;
import persistence.sql.ddl.fixtures.TransientTestEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class TransientQueryableTest {

    @Test
    @DisplayName("Transient 어노테이션이 붙은 필드는 생성되지 않아야 한다.")
    public void shouldNotCreateFieldWithTransientAnnotation() throws Exception {
        // Given
        Queryable transientQueryable = new TableColumn(TransientTestEntity.class.getDeclaredField("transientColumn"));

        // When
        StringBuilder query = new StringBuilder();
        transientQueryable.apply(query);

        // Then
        assertThat(query.toString()).isEmpty();
    }
}
