package persistence.sql.base;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnNameTest {

    @Test
    @DisplayName("아이디 컬럼의 객체를 생성한다")
    public void idColumn() throws NoSuchFieldException {
        ColumnName id = ColumnName.id(PersonV3.class);

        Field idField = PersonV3.class.getDeclaredField("id");
        assertThat(id).isEqualTo(new ColumnName(idField));
    }

    @Test
    @DisplayName("컬럼의 이름을 문자열로 반환한다")
    public void columnName() throws NoSuchFieldException {
        Class<PersonV3> clazz = PersonV3.class;
        ColumnName id = ColumnName.id(clazz);
        ColumnName name = new ColumnName(clazz.getDeclaredField("name"));

        assertAll(
                () -> assertThat(id.name()).isEqualTo("id"),
                () -> assertThat(name.name()).isEqualTo("nick_name")
        );
    }
}