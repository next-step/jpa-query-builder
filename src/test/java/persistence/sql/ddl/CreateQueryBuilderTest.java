package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateQueryBuilderTest {

    @DisplayName("@Entity가 붙은 class와 매핑되는 테이블을 만들수 있어야 한다.")
    @Test
    void createTableFromEntityAnnotatedClass() {

        final CreateQueryBuilder queryBuilder = CreateQueryBuilder.fromEntityAnnotatedClass(Person.class);

        final String expected = String.join(System.lineSeparator(),
                "CREATE TABLE Person (",
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,",
                "name VARCHAR,",
                "age INTEGER",
                ");"
        );

        assertThat(queryBuilder.toSQL()).isEqualTo(expected);

    }

    @DisplayName("@Entity가 붙은 클래스가 아닌 경우 예외가 발생해야 한다.")
    @Test
    void canNotCreateQueryBuilderFromNonEntityAnnotatedClass() {
        assertThatThrownBy(() -> CreateQueryBuilder.fromEntityAnnotatedClass(int.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("@Entity가 존재하지 않습니다");
    }
}
