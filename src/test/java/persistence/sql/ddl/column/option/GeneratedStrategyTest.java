package persistence.sql.ddl.column.option;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class GeneratedStrategyTest {
    private final GeneratedValueStrategy generatedValueStrategy = new H2GeneratedValueStrategy();

    @Test
    @DisplayName("GeneratedValue 에 따른 데이터베이스 아이디 전략 생성")
    public void generatedValueStrategy() throws NoSuchFieldException {
        // given
        Field id = GeneratedValueDummy.class.getDeclaredField("id");
        GeneratedValue generatedValue = id.getAnnotation(GeneratedValue.class);

        // when
        String result = generatedValueStrategy.generate(generatedValue);

        // then
        assertThat(result).isEqualTo("auto_increment");
    }

    static class GeneratedValueDummy {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }
}