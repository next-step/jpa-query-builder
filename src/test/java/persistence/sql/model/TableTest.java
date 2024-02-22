package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.constant.BasicColumnType;
import persistence.sql.entity.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[Unit] Table Class Create Test")
class TableTest {

    @DisplayName("전달된 컬럼에 primary key가 없을 경우 예외를 반환한다.")
    @Test
    void primary_key_not_found() throws NoSuchFieldException {

        List<Column> columns = List.of(Column.create(Person.class.getDeclaredField("name"), BasicColumnType.VARCHAR));

        assertThatThrownBy(() -> Table.create("test", columns))
            .isInstanceOf(PrimaryKeyNotFoundException.class);
    }

}
