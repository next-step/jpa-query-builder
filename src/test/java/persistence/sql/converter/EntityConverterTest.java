package persistence.sql.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.entity.Person;
import persistence.sql.model.Column;
import persistence.sql.model.NotEntityException;
import persistence.sql.model.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Unit] Entity Converter Test")
class EntityConverterTest {

    private final EntityConverter entityConverter = new EntityConverter(new H2Dialect());

    @DisplayName("Entity 어노테이션이 적용된 클래스가 전달될 경우 해당 클래스명을 name으로 가진 Table 타입의 객체를 반환한다.")
    @Test
    void convert_success() {

        Table table = entityConverter.convertEntityToTable(Person.class);

        assertThat(table.getName()).isEqualTo("PERSON");
    }

    @DisplayName("Entity 어노테이션이 적용된 클래스가 전달될 경우 해당 클래스의 필드를 column으로 가진 Table 타입의 객체를 반환한다.")
    @Test
    void convert_success_check_column() {

        List<String> declaredFieldNames = Arrays.stream(Person.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toList());

        Table table = entityConverter.convertEntityToTable(Person.class);
        List<Column> columns = table.getColumns();

        assertAll(() -> {
            assertThat(columns.size()).isEqualTo(declaredFieldNames.size());
            columns.forEach((column) -> assertThat(declaredFieldNames.contains(column.getName())).isTrue());
        });
    }

    @DisplayName("Entity 어노테이션이 적용되지 않은 클래스가 전달될 경우 예외를 반환한다.")
    @Test
    void fail_convert_by_parameter_is_not_entity() {
        assertThatThrownBy(() -> entityConverter.convertEntityToTable(String.class))
            .isInstanceOf(NotEntityException.class)
            .hasMessage("해당 클래스는 Entity가 아닙니다.");

    }


}
