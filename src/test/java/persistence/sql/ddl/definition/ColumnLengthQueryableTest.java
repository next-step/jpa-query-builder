package persistence.sql.ddl.definition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Queryable;
import persistence.sql.ddl.fixtures.ColumnLengthTestEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ColumnLengthQueryableTest {

    @Test
    @DisplayName("Column 어노테이션이 붙은 String 타입 필드는 255자로 생성된다.")
    public void shouldNotCreateFieldWithTransientAnnotation() throws Exception {
        // Given
        Queryable noColumnAnnotationField = new TableColumn(ColumnLengthTestEntity.class.getDeclaredField("column1"));

        // When
        StringBuilder query = new StringBuilder();
        noColumnAnnotationField.apply(query);

        // Then
        assertThat(query.toString()).isEqualTo("column1 VARCHAR(255), ");
    }

    @Test
    @DisplayName("Column 어노테이션의 length 값이 존재하는 String 타입 필드는 해당 길이로 생성된다.")
    public void shouldCreateFieldWithColumnAnnotationLength() throws Exception {
        // Given
        Queryable columnAnnotationField = new TableColumn(ColumnLengthTestEntity.class.getDeclaredField("column3"));

        // When
        StringBuilder query = new StringBuilder();
        columnAnnotationField.apply(query);

        // Then
        assertThat(query.toString()).isEqualTo("column3 VARCHAR(100), ");
    }

}
