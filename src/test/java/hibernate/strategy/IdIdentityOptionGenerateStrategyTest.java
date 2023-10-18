package hibernate.strategy;

import hibernate.entity.column.EntityId;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class IdIdentityOptionGenerateStrategyTest {

    private final IdIdentityOptionGenerateStrategy strategy = new IdIdentityOptionGenerateStrategy();

    @Test
    void id이고_GenerationType이_IDENTITY인_경우_acceptable하다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("id1");
        EntityId entityId = new EntityId(givenField);

        boolean actual = strategy.acceptable(entityId);
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"id2", "id3"})
    void id가_아니거나_IDENTITY가_아닌_경우_acceptable하지_않다(final String input) throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField(input);
        EntityId entityId = new EntityId(givenField);

        boolean actual = strategy.acceptable(entityId);
        assertThat(actual).isFalse();
    }

    @Test
    void Identity_컬럼옵션을_반환한다() {
        String actual = strategy.generateColumnOption();
        assertThat(actual).isEqualTo("auto_increment");
    }

    static class TestEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id1;

        @Id
        private Long id2;

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id3;
    }
}
