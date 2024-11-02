package persistence.sql.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnDataTest {

    @DisplayName("primary key인지 확인한다")
    @Test
    void isPrimaryKey() {
        Column column = new Column(new ColumnName("id"), "id", Long.class, List.of(ColumnOption.NOT_NULL), true);
        ColumnValue value = new ColumnValue("1");
        ColumnData columnData = new ColumnData(column, value);

        boolean primaryKey = columnData.isPrimaryKey();

        assertThat(primaryKey).isTrue();
    }

    @DisplayName("컬럼 이름을 반환한다")
    @Test
    void getName() {
        Column column = new Column(new ColumnName("column_name"), "id", Long.class, List.of(ColumnOption.NOT_NULL), true);
        ColumnValue value = new ColumnValue(1);
        ColumnData columnData = new ColumnData(column, value);

        String name = columnData.getName();

        assertThat(name).isEqualTo("column_name");
    }

    @DisplayName("컬럼 값을 반환한다")
    @Test
    void getValue() {
        Column column = new Column(new ColumnName("id"), "id", Long.class, List.of(ColumnOption.NOT_NULL), true);
        ColumnValue value = new ColumnValue(1);
        ColumnData columnData = new ColumnData(column, value);

        String columnValue = columnData.getValue();

        assertThat(columnValue).isEqualTo("1");
    }

    @DisplayName("IDENTITY 전략인지 확인한다")
    @Test
    void hasNotIdentityStrategy() {
        Column column = new Column(new ColumnName("id"), "id", Long.class, List.of(ColumnOption.IDENTITY), true);
        ColumnValue value = new ColumnValue(1);
        ColumnData columnData = new ColumnData(column, value);

        boolean notIdentityStrategy = columnData.hasNotIdentityStrategy();

        assertThat(notIdentityStrategy).isFalse();
    }

}
