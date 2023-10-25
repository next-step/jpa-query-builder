package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.JdbcTypeJavaClassMapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JdbcTypeJavaClassMappingTest {

    @DisplayName("타입에 알맞은 sql string을 반환한다")
    @Test
    void getTypeSqlString() {
        // given
        JdbcTypeJavaClassMapping jdbcTypeJavaClassMapping = new JdbcTypeJavaClassMapping();

        // when
        String typeSqlString = jdbcTypeJavaClassMapping.getType(String.class);
        String typeSqlInteger = jdbcTypeJavaClassMapping.getType(Integer.class);

        assertAll(
                () -> assertThat(typeSqlString).isEqualTo("varchar"),
                () -> assertThat(typeSqlInteger).isEqualTo("int")
        );

    }

}
