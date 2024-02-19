package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ColumnTest {
    private static final Class<Person> personClass = Person.class;

    @Test
    @DisplayName("@Column이 없으면 필드명을 그대로 사용한다.")
    void useFieldName() throws NoSuchFieldException {
        Column emailColumn = new Column(personClass.getDeclaredField("email"));

        assertThat(emailColumn.getColumnName()).isEqualTo("email");
    }


    @Entity
    private static class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @jakarta.persistence.Column(name = "nick_name")
        private String name;

        @jakarta.persistence.Column(nullable = false)
        private String email;
    }
}