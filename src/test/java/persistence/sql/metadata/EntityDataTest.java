package persistence.sql.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EntityDataTest {

    @DisplayName("컬럼 이름 목록을 반환한다")
    @Test
    void getColumnNames() {
        List<ColumnData> datas = List.of(
                new ColumnData(new Column(new ColumnName("id"), "id", Long.class, List.of(ColumnOption.NOT_NULL), true), new ColumnValue(1)),
                new ColumnData(new Column(new ColumnName("name"), "name", String.class, List.of(ColumnOption.NOT_NULL), false), new ColumnValue("name"))
        );

        EntityData entityData = new EntityData(datas);

        assertThat(entityData.getColumnNames()).containsExactly("id", "name");
    }

    @DisplayName("컬럼 값 목록을 반환한다")
    @Test
    void getColumnValues() {
        List<ColumnData> datas = List.of(
                new ColumnData(new Column(new ColumnName("id"), "id", Long.class, List.of(ColumnOption.NOT_NULL), true), new ColumnValue(1)),
                new ColumnData(new Column(new ColumnName("name"), "name", String.class, List.of(ColumnOption.NOT_NULL), false), new ColumnValue("name"))
        );

        EntityData entityData = new EntityData(datas);

        assertThat(entityData.getColumnValues()).containsExactly("1", "'name'");
    }

    @DisplayName("모든 컬럼 데이터를 반환한다")
    @Test
    void getAll() {
        List<ColumnData> datas = List.of(
                new ColumnData(new Column(new ColumnName("id"), "id", Long.class, List.of(ColumnOption.NOT_NULL), true), new ColumnValue(1)),
                new ColumnData(new Column(new ColumnName("name"), "name", String.class, List.of(ColumnOption.NOT_NULL), false), new ColumnValue("name"))
        );

        EntityData entityData = new EntityData(datas);

        assertThat(entityData.getAll()).containsExactlyElementsOf(datas);
    }
}
