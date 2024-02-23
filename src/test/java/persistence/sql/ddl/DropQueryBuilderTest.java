package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.domain.PersonWithCustomTableNameAndTransientColumn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DropQueryBuilderTest {

    @DisplayName("@Entity가 붙은 클래스와 매핑되는 DDL 생성 가능")
    @Test
    void canCreateDDLFromEntityAnnotatedClass() {
        final DropQueryBuilder builder = new DropQueryBuilder(Person.class);
        final String expected = "DROP TABLE IF EXISTS Person;";
        assertThat(builder.toSQL()).isEqualTo(expected);
    }

    @DisplayName("@Entity가 붙어있지 않은 경우, DDL 생성 불가")
    @Test
    void canNotCreateQueryBuilderFromNonEntityAnnotatedClass() {
        assertThatThrownBy(() -> new DropQueryBuilder(int.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("@Entity가 존재하지 않습니다");
    }

    @DisplayName("@Table name 속성과 매핑되는 DDL 생성 가능")
    @Test
    void canCreateDDLUsingCustomTableName() {
        final DropQueryBuilder builder = new DropQueryBuilder(PersonWithCustomTableNameAndTransientColumn.class);
        final String expected = "DROP TABLE IF EXISTS users;";
        assertThat(builder.toSQL()).isEqualTo(expected);
    }
}
