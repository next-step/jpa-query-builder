package persistence.sql.dbms.mapper.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class H2TypeMapperTest {

    @DisplayName("Java의 {0} Type은 H2 DBMS의 {1} Type과 매핑된다")
    @ParameterizedTest
    @ArgumentsSource(H2TypeMapperTestArgumentsProvider.class)
    void create(Class<?> javaType, String dbType) {
        H2TypeMapper h2TypeMapper = new H2TypeMapper();

        assertThat(h2TypeMapper.create(javaType)).isEqualTo(dbType);
    }

    static class H2TypeMapperTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(Long.class, "BIGINT"),
                    Arguments.of(Integer.class, "INT"),
                    Arguments.of(int.class, "INT"),
                    Arguments.of(String.class, "VARCHAR")
            );
        }
    }
}