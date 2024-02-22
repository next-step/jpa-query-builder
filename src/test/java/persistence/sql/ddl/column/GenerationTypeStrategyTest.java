package persistence.sql.ddl.column;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GenerationTypeStrategyTest {

    @Test
    @DisplayName("GenerationTypeStrategy 을 찾을 때 generationValue 가 null 인 경우 null 을 반환한다.")
    void fromByNull() {
        // given & when
        GenerationTypeStrategy result = GenerationTypeStrategy.from(null);

        // then
        assertThat(result).isNull();
    }

}
