package persistence.sql.ddl.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;


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
        Assertions.assertAll(()-> assertThat(id).isEqualTo("id"),
                ()->assertThat(name).isEqualTo("name"),
                ()->assertThat(age).isEqualTo("age"));
    }

}