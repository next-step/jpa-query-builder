package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.constant.ColumnType;
import persistence.sql.entity.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[Unit] Table Class Create Test")
class TableTest {

    @DisplayName("전달된 컬럼에 primary key가 없을 경우 예외를 반환한다.")
    @Test
    void primary_key_not_found() throws NoSuchFieldException {

        List<Column> columns = List.of(Column.create(Person.class.getDeclaredField("name"), ColumnType.VARCHAR));

        assertThatThrownBy(() -> Table.create(Person.class, columns))
            .isInstanceOf(PrimaryKeyNotFoundException.class);
    }

    @DisplayName("전달된 클래스의 @Table 어노테이션에 name 옵션이 있을 경우 인스턴스의 name을 옵션값으로 설정하여 생성한다.")
    @Test
    void create_with_table_name() throws NoSuchFieldException {

        List<Column> columns = List.of(Column.create(Person.class.getDeclaredField("id"), ColumnType.BIGINT));

        jakarta.persistence.Table annotation = Person.class.getDeclaredAnnotation(jakarta.persistence.Table.class);
        Table table = Table.create(Person.class, columns);

        assertThat(table.getName()).isEqualTo(annotation.name());
    }
}
