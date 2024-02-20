package persistence.sql.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.identity.H2IdentityColumnSupport;
import persistence.sql.dialect.identity.IdentityColumnSupport;

import java.sql.Types;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class H2DialectTest {

    private final Dialect dialect = new H2Dialect();
    
    @DisplayName("H2IdentityColumnSupport 객체를 반환한다")
    @Test
    public void returnH2IdentityColumnSupport() throws Exception {
        // when
        final IdentityColumnSupport identityColumnSupport = dialect.getIdentityColumnSupport();

        // then
        assertThat(identityColumnSupport).isInstanceOf(H2IdentityColumnSupport.class);
    }

    @DisplayName("Type 을 받아 H2 DB 의 column type 을 반환한다")
    @Test
    public void createColumnType() throws Exception {
        // given
        final int stringType = Types.VARCHAR;
        final int longType = Types.BIGINT;
        final int integerType = Types.INTEGER;

        // when
        final String stringColumnType = dialect.convertColumnType(stringType, 255);
        final String longColumnType = dialect.convertColumnType(longType, 255);
        final String integerColumnType = dialect.convertColumnType(integerType, 255);

        // then
        assertAll(
                () -> assertThat(stringColumnType).isEqualTo("varchar(255)"),
                () -> assertThat(longColumnType).isEqualTo("bigint"),
                () -> assertThat(integerColumnType).isEqualTo("integer")
        );
    }

    @DisplayName("Type 으로 H2 DB 의 column type 을 반환할 때 적절한 type 을 찾지 못하면 예외를 를 반환한다")
    @Test
    public void createNotfoundColumnType() throws Exception {
        // given
        final int doubleType = Types.DOUBLE;

        // when then
        assertThatThrownBy(() -> dialect.convertColumnType(doubleType, 255))
                .isInstanceOf(DialectException.class)
                .hasMessage("NotFount ColumnType for 8 in H2Dialect");
    }
}
