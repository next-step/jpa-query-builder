package persistence.sql.meta;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMetasTest {

    @Test
    @DisplayName("Transient 컬럼 제외")
    void exceptTransient() {
        ColumnMetas columnMetas = ColumnMetas.of(Person.class.getDeclaredFields());
        ColumnMetas exceptTransient = columnMetas.exceptTransient();
        assertThat(exceptTransient.getColumnsClause()).isEqualTo("id, nick_name, old, email");
    }

    @Test
    @DisplayName("Id 컬럼 추출")
    void idColumns() {
        ColumnMetas columnMetas = ColumnMetas.of(Person.class.getDeclaredFields());
        ColumnMetas idColumns = columnMetas.idColumns();
        assertThat(idColumns.getColumnsClause()).isEqualTo("id");
    }
}
