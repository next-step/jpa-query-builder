package hibernate.entity.strategy;

import hibernate.entity.EntityId;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class IdIdentityOptionGenerateStrategyTest {

    private final IdIdentityOptionGenerateStrategy strategy = new IdIdentityOptionGenerateStrategy();

    @Test
    void id이고_GenerationType이_IDENTITY인_경우_acceptable하다() throws NoSuchFieldException {
        EntityId entityId = new EntityId(TestEntity.class.getDeclaredField("id1"));
        boolean actual = strategy.acceptable(entityId);
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"id2", "id3"})
    void id가_아니거나_IDENTITY가_아닌_경우_acceptable하지_않다(final String input) throws NoSuchFieldException {
        EntityId entityId = new EntityId(TestEntity.class.getDeclaredField(input));
        boolean actual = strategy.acceptable(entityId);
        assertThat(actual).isFalse();
    }

    @Test
    void Identity_컬럼옵션을_반환한다() {
        String actual = strategy.generateColumnOption();
        assertThat(actual).isEqualTo("auto_increment");
    }

    class TestEntity {
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
