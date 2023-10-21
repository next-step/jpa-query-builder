package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.NoEntityException;
import persistence.meta.EntityMeta;
import persistence.sql.QueryGenerator;

class QueryGeneratorTest {
    @Test
    @DisplayName("엔티티클래스정보가 비어 있으면 예외가 발생한다.")
    void emptyClass() {
        assertThatExceptionOfType(NoEntityException.class)
                .isThrownBy(() -> QueryGenerator.from((Class) null));
    }

    @Test
    @DisplayName("엔티티정보가 비어 있으면 예외가 발생한다.")
    void emptyEntityMeta() {
        assertThatExceptionOfType(NoEntityException.class)
                .isThrownBy(() -> QueryGenerator.from((EntityMeta) null));
    }

}
