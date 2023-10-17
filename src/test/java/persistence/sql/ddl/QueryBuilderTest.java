package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.NoEntityException;
import persistence.testFixtures.NoHasEntity;

@DisplayName("QueryBuilder 테스트")
class QueryBuilderTest {

    @Test
    @DisplayName("엔티티가 없으면 예외가 발생한다")
    void noEntity() {
        assertThatExceptionOfType(NoEntityException.class)
                .isThrownBy(() -> new QueryBuilder<>(NoHasEntity.class));
    }

}
