package persistence.sql.ddl.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class ColumnsTest {

    @Test
    @DisplayName("column들의 이름을 가져온다.")
    void getColumnsName() {
        //Given
        Columns columns = new Columns(Person.class);

        //When
        String id = columns.getColumn("id").getName();
        String name = columns.getColumn("name").getName();
        String age = columns.getColumn("age").getName();

        //Then
        assertAll(()-> assertThat(id).isEqualTo("id"),
                ()->assertThat(name).isEqualTo("name"),
                ()->assertThat(age).isEqualTo("age"));
    }

    @Test
    @DisplayName("@Id 컬럼만 가져온다")
    void getIdColumn() {
        //Given
        Columns columns = new Columns(Person.class);
        Columns idColumns = columns.getIdColumns();

        //When
        Column id = idColumns.getColumn("id");
        Column name = idColumns.getColumn("name");
        Column age = idColumns.getColumn("age");

        //Then
        assertAll(()-> assertThat(id.getName()).isEqualTo("id"),
                ()->assertThat(name).isNull(),
                ()->assertThat(age).isNull());
    }


}