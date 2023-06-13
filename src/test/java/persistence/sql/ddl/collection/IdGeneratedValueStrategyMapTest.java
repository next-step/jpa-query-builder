package persistence.sql.ddl.collection;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdGeneratedValueStrategyMapTest {

    @DisplayName("GenerationType.IDENTITY 에 해당하는 GeneratedStrategy 를 반환한다.")
    @Test
    void generatedStrategyOfIdentity() {
        final IdGeneratedValueStrategyMap idGeneratedValueStrategyMap = new IdGeneratedValueStrategyMap();
        final String generatedStrategy = idGeneratedValueStrategyMap.get(GenerationType.IDENTITY);

        assertThat(generatedStrategy).isEqualTo("auto_increment");
    }

    @DisplayName("만족하는 GeneratedStrategy 가 없으면 예외를 발생시킨다.")
    @Test
    void nowFoundGeneratedStrategy() {
        final IdGeneratedValueStrategyMap idGeneratedValueStrategyMap = new IdGeneratedValueStrategyMap();

        assertThatThrownBy(() -> idGeneratedValueStrategyMap.get(GenerationType.SEQUENCE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy for type SEQUENCE");
    }

}
