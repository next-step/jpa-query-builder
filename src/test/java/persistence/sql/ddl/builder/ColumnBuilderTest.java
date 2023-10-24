package persistence.sql.ddl.builder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.h2.H2Dialect;
import persistence.sql.meta.EntityMeta;
import persistence.sql.meta.MetaFactory;

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
    @DisplayName("H2 컬럼목록 빌드 정상 테스트")
    void getColumnDefinition() {
        EntityMeta entityMeta = MetaFactory.get(TestDomain.class);
        ColumnBuilder columnBuilder = new ColumnBuilder(H2Dialect.getInstance(), entityMeta.getColumnMetas());
        String columnDefinition = columnBuilder.buildColumnDefinition();
        assertAll(
                () -> assertThat(columnDefinition).isEqualTo("a BIGINT PRIMARY KEY, b INT, c VARCHAR"),
                () -> assertThat(columnDefinition.split(",").length).isEqualTo(3)
        );
    }

}
