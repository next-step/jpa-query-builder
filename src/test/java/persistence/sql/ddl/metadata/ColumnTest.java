package persistence.sql.ddl.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.IdentityStrategy;
import persistence.sql.ddl.fixture.IncludeId;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ColumnTest {

    @DisplayName("필드를 받아 컬럼으로 변환한다")
    @Test
    void fromField() throws Exception {
        Field field = IncludeId.class.getDeclaredField("name");
        Column column = Column.from(field);

        assertSoftly(softly -> {
            softly.assertThat(column.getName()).isEqualTo("name");
            softly.assertThat(column.getSqlType()).isEqualTo("varchar(255)");
            softly.assertThat(column.primaryKey()).isFalse();
        });
    }

    @DisplayName("@Id annotation이 추가되면 not null 제약조건을 갖는다.")
    @Test
    void idField() throws Exception {
        Field field = IncludeId.class.getDeclaredField("id");
        Column column = Column.from(field);

        assertSoftly(softly -> {
            softly.assertThat(column.getName()).isEqualTo("id");
            softly.assertThat(column.getSqlType()).isEqualTo("bigint");
            softly.assertThat(column.primaryKey()).isTrue();
        });
    }

    @DisplayName("@GeneratedValue(strategy = GenerationType.IDENTITY) annotation이 추가되면 AUTO_INCREMENT 제약조건을 갖는다.")
    @Test
    void autoIncrementField() throws Exception {
        Field field = IdentityStrategy.class.getDeclaredField("id");
        Column autoIncrementColumn = Column.from(field);

        assertThat(autoIncrementColumn.options()).contains(ColumnOption.AUTO_INCREMENT);
    }
}
