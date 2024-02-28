package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.EntityBinder;
import persistence.study.sql.ddl.Person2;
import persistence.study.sql.ddl.Person3;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ColumnTest {

    @DisplayName("올바른 컬럼이 생성되는지 확인")
    @ParameterizedTest
    @MethodSource
    void newColumn(Column column, String name, SqlType type, List<SqlConstraint> constraints) throws NoSuchFieldException {
        assertSoftly(softly -> {
            softly.assertThat(column.getName()).isEqualTo(name);
            softly.assertThat(column.getType()).isEqualTo(type);
            softly.assertThat(column.getConstraints()).isEqualTo(constraints);
        });
    }

    private static Stream<Arguments> newColumn() throws NoSuchFieldException {
        Class<?> clazz = Person3.class;
        Field idField = clazz.getDeclaredField("id");
        Field nameField = clazz.getDeclaredField("name");
        Field ageField = clazz.getDeclaredField("age");
        Field emailField = clazz.getDeclaredField("email");
        return Stream.of(
                Arguments.arguments(new Column(idField), "id", SqlType.BIGINT, List.of(SqlConstraint.PRIMARY_KEY)),
                Arguments.arguments(new Column(nameField), "nick_name", SqlType.VARCHAR, List.of()),
                Arguments.arguments(new Column(ageField), "old", SqlType.INTEGER, List.of()),
                Arguments.arguments(new Column(emailField), "email", SqlType.VARCHAR, List.of(SqlConstraint.NOT_NULL))
        );
    }

    @DisplayName("@Transient 어노테이션이 달린 필드를 넣었을 경우 IllegarArgumentException을 던진다.")
    @Test
    void newColumnWithException() throws NoSuchFieldException {
        Class<Person3> clazz = Person3.class;
        Field field = clazz.getDeclaredField("index");

        assertThatThrownBy(() -> new Column(field))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("This field is not a column: index");
    }
}
