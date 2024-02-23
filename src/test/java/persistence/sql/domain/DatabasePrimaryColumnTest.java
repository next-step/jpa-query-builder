package persistence.sql.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabasePrimaryColumnTest {


    @Test
    void should_return_true_if_identity_strategy() throws NoSuchFieldException {
        DatabasePrimaryColumn identityColumn = DatabasePrimaryColumn.fromField(IdentityStrategyClass.class.getDeclaredField("generationType"), null);
        DatabasePrimaryColumn sequenceColumn = DatabasePrimaryColumn.fromField(SequenceStrategyClass.class.getDeclaredField("generationType"), null);

        assertThat(identityColumn.hasIdentityStrategy()).isTrue();
        assertThat(sequenceColumn.hasIdentityStrategy()).isFalse();
    }

    private class IdentityStrategyClass {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private GenerationType generationType;
    }

    private class SequenceStrategyClass {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private GenerationType generationType;
    }


}
