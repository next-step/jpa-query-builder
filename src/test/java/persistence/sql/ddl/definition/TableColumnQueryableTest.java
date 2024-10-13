package persistence.sql.ddl.definition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixtures.NullableTestEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class TableColumnQueryableTest {

    @Test
    @DisplayName("Column 어노테이션을 기반으로 nullable 속성을 판단한다. Column이 없으면 nullable이 true여야 한다.")
    public void shouldDetermineNullableAttributeBasedOnColumnAnnotation() throws Exception {
        // Given
        TableColumn column = new TableColumn(NullableTestEntity.class.getDeclaredField("nullableColumn1"));

        // When
        StringBuilder query = new StringBuilder();
        column.apply(query);

        // Then
        assertThat(query.toString()).isEqualTo("nullableColumn1 VARCHAR(255), ");
    }

    @Test
    @DisplayName("Column 어노테이션을 기반으로 nullable 속성을 판단한다. nullable이 true이면 NOT NULL이 없어야 한다.")
    public void shouldNotContainNotNullWhenNullableIsTrue() throws Exception {
        // Given
        TableColumn column = new TableColumn(NullableTestEntity.class.getDeclaredField("nullableColumn2"));

        // When
        StringBuilder query = new StringBuilder();
        column.apply(query);

        // Then
        assertThat(query.toString()).isEqualTo("nullableColumn2 VARCHAR(255), ");
    }

    @Test
    @DisplayName("Column 어노테이션을 기반으로 nullable 속성을 판단한다. nullable이 false이면 NOT NULL이 있어야 한다.")
    public void shouldContainNotNullWhenNullableIsFalse() throws Exception {
        // Given
        TableColumn column = new TableColumn(NullableTestEntity.class.getDeclaredField("nonNullableColumn"));

        // When
        StringBuilder query = new StringBuilder();
        column.apply(query);

        // Then
        assertThat(query.toString()).isEqualTo("nonNullableColumn VARCHAR(255) NOT NULL, ");
    }
}
