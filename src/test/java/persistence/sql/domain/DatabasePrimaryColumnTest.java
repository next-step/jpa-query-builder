package persistence.sql.domain;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DatabasePrimaryColumnTest {

    static Stream<Arguments> generateStrategyArguments() {
        return Stream.of(
                Arguments.arguments(GenerationType.IDENTITY, true),
                Arguments.arguments(GenerationType.AUTO, true),
                Arguments.arguments(GenerationType.SEQUENCE, false)
        );
    }

    @ParameterizedTest
    @MethodSource("generateStrategyArguments")
    void should_return_true_if_identity_strategy(GenerationType type, boolean expectResult) {
        ColumnName name = new ColumnName("test");
        DatabasePrimaryColumn primaryColumn = new DatabasePrimaryColumn(name, null, null, type);

        assertThat(primaryColumn.hasIdentityStrategy()).isEqualTo(expectResult);
    }


}
