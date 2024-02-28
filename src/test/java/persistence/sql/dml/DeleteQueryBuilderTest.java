package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.EntityBinder;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person1;
import persistence.study.sql.ddl.Person2;
import persistence.study.sql.ddl.Person3;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    @DisplayName("Person을 이용하여 delete 쿼리 생성하기")
    @Test
    void build() {
        Table table = new Table(Person3.class);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(table);

        String result = deleteQueryBuilder.build();

        String deleteQuery = "DELETE FROM users;";
        assertThat(result).isEqualTo(deleteQuery);
    }

    @DisplayName("person을 이용하여 deleteById 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildById(Table table, Object instance, String deleteByIdQuery) {
        PKColumn pkColumn = table.getPKColumn();
        EntityBinder entityBinder = new EntityBinder(instance);
        Object id = entityBinder.getValue(pkColumn);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(table);

        String result = deleteQueryBuilder.buildById(id);

        assertThat(result).isEqualTo(deleteByIdQuery);
    }

    private static Stream<Arguments> buildById() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), new Person1(1L, "q", 1), "DELETE FROM person1 WHERE id=1;"),
                Arguments.arguments(new Table(Person2.class), new Person2(2L, "w", 2, "email@email.com"), "DELETE FROM person2 WHERE id=2;"),
                Arguments.arguments(new Table(Person3.class), new Person3(3L, "e", 3, "email@email.com"), "DELETE FROM users WHERE id=3;")
        );
    }
}
