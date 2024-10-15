package persistence.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.meta.EntityField;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

class H2DialectTest {
    @ParameterizedTest
    @CsvSource(value = {"0:VARCHAR", "1:INTEGER", "2:BIGINT"}, delimiter = ':')
    @DisplayName("자바 타입을 DB 타입으로 변환한다.")
    void getDbTypeName(int index, String expected) {
        // given
        final H2Dialect dialect = new H2Dialect();
        final Field field = EntityWithString.class.getDeclaredFields()[index];
        final EntityField entityField = new EntityField(field);

        // when
        final String dbTypeName = dialect.getDbTypeName(entityField);

        // then
        assertThat(dbTypeName).isEqualTo(expected);
    }

    static class EntityWithString {
        private String var1;
        private Integer var2;
        private Long var3;
    }
}
