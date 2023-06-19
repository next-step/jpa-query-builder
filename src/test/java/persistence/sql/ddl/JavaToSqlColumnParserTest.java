package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.Dialect;
import persistence.dialect.H2DbDialect;
import persistence.dialect.type.JavaToH2Type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class JavaToSqlColumnParserTest {

    @DisplayName("Java Type 을 SQL TYPE 으로 변환하기 위해서 Dialect 를 주입해야 한다.")
    @Test
    void create() {
        assertThatCode(() -> new JavaToSqlColumnParser(new Dialect() {
            @Override
            public boolean support(String dbType) {
                return false;
            }
            @Override
            public String getType(Class<?> type) {
                return null;
            }
        })).doesNotThrowAnyException();
    }

    @DisplayName("Dialet 에 맞는 SQL TYPE 으로 변환한다.")
    @Test
    void parse() {
        final H2DbDialect h2DbDialect = new H2DbDialect();

        final String longType = h2DbDialect.getType(Long.class);
        assertThat(longType).isEqualTo(JavaToH2Type.LONG.getDbType());

        final String stringType = h2DbDialect.getType(String.class);
        assertThat(stringType).isEqualTo(JavaToH2Type.STRING.getDbType());

        final String integerType = h2DbDialect.getType(Integer.class);
        assertThat(integerType).isEqualTo(JavaToH2Type.INTEGER.getDbType());
    }

}
