package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnBuilderTest {

    @Entity
    private static class TestDomain {
        @Id
        private Long a;
        private Integer b;
        private String c;
    }

    @Test
    @DisplayName("컬럼목록 빌드 정상 테스트")
    void getColumnDefinition() {
        String columnDefinition = ColumnBuilder.getColumnDefinition(TestDomain.class.getDeclaredFields());
        assertAll(
                () -> assertThat(columnDefinition).isEqualTo("a BIGINT PRIMARY KEY, b INT, c VARCHAR"),
                () -> assertThat(columnDefinition.split(",").length).isEqualTo(3)
        );
    }

}