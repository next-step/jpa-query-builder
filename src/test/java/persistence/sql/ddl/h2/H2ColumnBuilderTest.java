package persistence.sql.ddl.h2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class H2ColumnBuilderTest {

    private static final H2ColumnBuilder columnBuilder = H2ColumnBuilder.getInstance();

    @Entity
    private static class TestDomain {
        @Id
        private Long a;
        private Integer b;
        private String c;
    }

    @Test
    @DisplayName("H2 컬럼목록 빌드 정상 테스트")
    void getColumnDefinition() {
        String columnDefinition = columnBuilder.getColumnDefinition(TestDomain.class.getDeclaredFields());
        assertAll(
                () -> assertThat(columnDefinition).isEqualTo("a BIGINT PRIMARY KEY, b INT, c VARCHAR"),
                () -> assertThat(columnDefinition.split(",").length).isEqualTo(3)
        );
    }

}
