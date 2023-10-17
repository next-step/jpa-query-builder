package persistence.sql.ddl.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.type.DataType;

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
        assertAll(() -> assertThat(id).isEqualTo("id"),
                () -> assertThat(name).isEqualTo("name"),
                () -> assertThat(age).isEqualTo("age"));
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
        assertAll(() -> assertThat(id.getName()).isEqualTo("id"),
                () -> assertThat(name).isNull(),
                () -> assertThat(age).isNull());
    }

    @Test
    @DisplayName("컬럼 타입별 DB 타입을 가져온다")
    void getColumnType() {
        //Given
        Columns columns = new Columns(Person.class);

        //When
        DataType id = columns.getColumn("id").getDataType();
        DataType name = columns.getColumn("name").getDataType();
        DataType age = columns.getColumn("age").getDataType();

        //Then
        assertAll(() -> assertThat(id.getName()).isEqualTo("BIGINT"),
                () -> assertThat(name.getName()).isEqualTo("VARCHAR"),
                () -> assertThat(age.getName()).isEqualTo("INTEGER"));
    }

    @Test
    @DisplayName("컬럼의 length 값을 가져온다")
    void getColumnLength() {
        //Given
        Columns columns = new Columns(Person.class);

        //When
        Integer id = columns.getColumn("id").getLength();
        Integer name = columns.getColumn("name").getLength();
        Integer age = columns.getColumn("age").getLength();

        //Then
        assertAll(() -> assertThat(id).isNull(),
                () -> assertThat(name).isEqualTo(255),
                () -> assertThat(age).isNull());
    }


}