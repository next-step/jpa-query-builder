package persistence.sql.ddl.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.type.DataType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class ColumnTypesTest {

    private final String ID = "id";
    private final String NAME = "nick_name";
    private final String AGE = "old";

    @Test
    @DisplayName("column들의 이름을 가져온다.")
    void getColumnsName() {
        //Given
        ColumnTypes columnTypes = new ColumnTypes(new Person());

        //When
        String id = columnTypes.getColumn(ID).getName();
        String name = columnTypes.getColumn(NAME).getName();
        String age = columnTypes.getColumn(AGE).getName();

        //Then
        assertAll(() -> assertThat(id).isEqualTo("id"),
                () -> assertThat(name).isEqualTo("nick_name"),
                () -> assertThat(age).isEqualTo("old"));
    }

    @Test
    @DisplayName("@Id 컬럼만 가져온다")
    void getIdColumn() {
        //Given
        ColumnTypes columnTypes = new ColumnTypes(new Person());
        List<ColumnId> idColumnTypes = columnTypes.getIdColumns();
        //When
        long count = idColumnTypes.stream().filter(ColumnId::isId).count();
        //then
        assertThat(count).isEqualTo(idColumnTypes.size());

    }

    @Test
    @DisplayName("컬럼 타입별 DB 타입을 가져온다")
    void getColumnType() {
        //Given
        ColumnTypes columnTypes = new ColumnTypes(new Person());

        //When
        DataType id = columnTypes.getColumn(ID).getDataType();
        DataType name = columnTypes.getColumn(NAME).getDataType();
        DataType age = columnTypes.getColumn(AGE).getDataType();

        //Then
        assertAll(() -> assertThat(id.getName()).isEqualTo("BIGINT"),
                () -> assertThat(name.getName()).isEqualTo("VARCHAR"),
                () -> assertThat(age.getName()).isEqualTo("INTEGER"));
    }

    @Test
    @DisplayName("컬럼의 length 값을 가져온다")
    void getColumnLength() {
        //Given
        ColumnTypes columnTypes = new ColumnTypes(new Person());

        //When
        String id = columnTypes.getColumn(ID).getLength();
        String name = columnTypes.getColumn(NAME).getLength();
        String age = columnTypes.getColumn(AGE).getLength();

        //Then
        assertAll(() -> assertThat(id).isEqualTo(""),
                () -> assertThat(name).isEqualTo("(255)"),
                () -> assertThat(age).isEqualTo(""));
    }
}