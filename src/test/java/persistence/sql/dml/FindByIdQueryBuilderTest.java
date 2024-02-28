package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
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

class FindByIdQueryBuilderTest {

    @DisplayName("Person을 이용하여 findById 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void build(Table table, Object person, String findByIdQuery) {
        PKColumn pkColumn = table.getPKColumn();
        EntityBinder entityBinder = new EntityBinder(person);

        Object id = entityBinder.getValue(pkColumn);
        FindByIdQueryBuilder findByIdQueryBuilder = new FindByIdQueryBuilder(table, id);

        String result = findByIdQueryBuilder.build();

        assertThat(result).isEqualTo(findByIdQuery);
    }

    private static Stream<Arguments> build() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), new Person1(1L, "qwer", 1), "SELECT id,name,age FROM person1 WHERE id=1;"),
                Arguments.arguments(new Table(Person2.class), new Person2(2L, "qwert", 2, "email@email.com"), "SELECT id,nick_name,old,email FROM person2 WHERE id=2;"),
                Arguments.arguments(new Table(Person3.class), new Person3(3L, "qwerty", 3, "email2@email.com"), "SELECT id,nick_name,old,email FROM users WHERE id=3;")
        );
    }
}
