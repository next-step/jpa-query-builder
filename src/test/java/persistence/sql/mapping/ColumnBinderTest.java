package persistence.sql.mapping;

import jakarta.persistence.Transient;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.PersonV3;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnBinderTest {

    private final ColumnTypeMapper columnTypeMapper = ColumnTypeMapper.getInstance();

    private final ColumnBinder columnBinder = new ColumnBinder(columnTypeMapper);

    @DisplayName("Entity 의 class 를 이용해 Column 리스트를 반환한다")
    @Test
    public void createColumns() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final int fieldsNum = (int) Arrays.stream(clazz.getDeclaredFields()).filter(field -> !field.isAnnotationPresent(Transient.class)).count();

        // when
        final List<Column> columns = columnBinder.createColumns(clazz);

        // then
        assertThat(columns).hasSize(fieldsNum)
                .extracting("value.originalType")
                .contains(Long.class, String.class, Integer.class, String.class);
    }

    @DisplayName("Entity 의 object 를 이용해 값이 있는 Column 리스트를 반환한다")
    @Test
    public void createColumnsWithValue() throws Exception {
        // given
        final long id = 1L;
        final String name = "name";
        final int age = 1;
        final String mail = "email@domain.com";
        final int index = 0;
        final PersonV3 person = new PersonV3(id, name, age, mail, index);
        final Class<? extends PersonV3> clazz = person.getClass();
        final int fieldsNum = (int) Arrays.stream(clazz.getDeclaredFields()).filter(field -> !field.isAnnotationPresent(Transient.class)).count();

        // when
        final List<Column> columns = columnBinder.createColumns(person);

        // then
        assertThat(columns).hasSize(fieldsNum)
                .extracting("value.value", "value.originalType")
                .contains(
                        Tuple.tuple(id, Long.class),
                        Tuple.tuple(name, name.getClass()),
                        Tuple.tuple(age, Integer.class),
                        Tuple.tuple(mail, mail.getClass())
                );
    }

    @DisplayName("필드와 컬럼 애너테이션을 이용해 컬럼 이름을 추출한다")
    @Test
    public void toColumnName() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Field idField = clazz.getDeclaredField("id");
        final String idFieldName = idField.getName();
        final Field nameField = clazz.getDeclaredField("name");
        final String nameAnnotationName = nameField.getAnnotation(jakarta.persistence.Column.class).name();

        // when
        final String idColumnName = ColumnBinder.toColumnName(idField);
        final String nameColumnName = ColumnBinder.toColumnName(nameField);

        // then
        assertAll(
                () -> assertThat(idColumnName).isEqualTo(idFieldName),
                () -> assertThat(nameColumnName).isEqualTo(nameAnnotationName)
        );
    }

}
