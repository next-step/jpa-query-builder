package persistence.sql.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class H2DialectTest {

    private final Dialect dialect = new H2Dialect();

    @DisplayName("필드의 Type Name 을 받아 H2 DB 의 column type 을 반환한다")
    @Test
    public void createColumnType() throws Exception {
        // given
        final String stringName = String.class.getSimpleName();
        final String longName = Long.class.getSimpleName();
        final String integerName = Integer.class.getSimpleName();

        // when
        final String stringColumnType = dialect.createColumnQuery(stringName);
        final String longColumnType = dialect.createColumnQuery(longName);
        final String integerColumnType = dialect.createColumnQuery(integerName);

        // then
        assertAll(
                () -> assertThat(stringColumnType).isEqualTo("VARCHAR(255)"),
                () -> assertThat(longColumnType).isEqualTo("BIGINT"),
                () -> assertThat(integerColumnType).isEqualTo("INTEGER")
        );
    }

    @DisplayName("필드의 Type Name 으로 H2 DB 의 column type 을 반환할 때 적절한 type 을 찾지 못하면 VARCHAR 를 반환한다")
    @Test
    public void throwNotfoundColumnType() throws Exception {
        // given
        final String doubleName = Double.class.getSimpleName();

        // when
        final String doubleColumnType = dialect.createColumnQuery(doubleName);

        // then
        assertThat(doubleColumnType).isEqualTo("VARCHAR(255)");
    }

    @DisplayName("H2 DB 문법에 맞는 PK 예약어를 반환한다")
    @Test
    public void getH2Pk() throws Exception {
        // when
        final String h2Pk = dialect.getPk();

        // then
        assertThat(h2Pk).isEqualTo("PRIMARY KEY");
    }

}