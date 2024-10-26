package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.EntityWithColumn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class DmlColumnTest {

    @DisplayName("field와 instance를 받아 객체를 생성한다")
    @Test
    void createInstance() throws Exception {
        EntityWithColumn entityWithColumn = EntityWithColumn.class.getConstructor().newInstance();

        assertThatCode(() -> DmlColumn.from(entityWithColumn, entityWithColumn.getClass().getDeclaredField("withColumn")))
                .doesNotThrowAnyException();
    }

    @DisplayName("컬럼 이름을 반환한다")
    @Test
    void getColumnName() throws Exception {
        EntityWithColumn entityWithColumn = EntityWithColumn.class.getConstructor().newInstance();
        DmlColumn dmlColumn = DmlColumn.from(entityWithColumn, entityWithColumn.getClass().getDeclaredField("withColumn"));

        String actual = dmlColumn.getName();

        assertThat(actual).isEqualTo("my_column");
    }

}
