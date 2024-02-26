package persistence.sql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.study.sql.ddl.Person3;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class SqlConstraintTest {

    @Test
    @DisplayName("Column 어노테이션에서 제약 조건을 올바르게 추출하는지 확인")
    void getConstraints() throws NoSuchFieldException {
        Class<?> clazz = Person3.class;
        Field field = clazz.getDeclaredField("email");
        Column column = field.getDeclaredAnnotation(Column.class);

        List<SqlConstraint> result = SqlConstraint.of(column);

        assertThat(result).containsExactlyInAnyOrder(SqlConstraint.NOT_NULL);
    }

    @Test
    @DisplayName("Id 어노테이션에서 제약 조건을 올바르게 추출하는지 확인")
    void getConstraintById() throws NoSuchFieldException {
        Class<?> clazz = Person3.class;
        Field field = clazz.getDeclaredField("id");
        Id id = field.getDeclaredAnnotation(Id.class);

        List<SqlConstraint> result = SqlConstraint.of(id);

        assertThat(result).containsExactlyInAnyOrder(SqlConstraint.PRIMARY_KEY);
    }
}
