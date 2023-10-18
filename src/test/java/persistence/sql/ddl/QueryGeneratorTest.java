package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.NoEntityException;

class QueryGeneratorTest {
    @Test
    @DisplayName("엔티티가 비어 있으면 예외가 발생한다.")
    void emptyEntity() {
        assertThatExceptionOfType(NoEntityException.class)
                .isThrownBy(() -> QueryGenerator.from(null));
    }

}
