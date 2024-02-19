package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class H2ColumnTest {
    private static final Class<Person> personClass = Person.class;

    @Test
    @DisplayName("@Column이 없으면 필드명을 그대로 사용한다.")
    void useFieldName() throws NoSuchFieldException {
        H2Column emailH2Column = new H2Column(personClass.getDeclaredField("email"));

        assertThat(emailH2Column.getColumnName()).isEqualTo("email");
    }

    @Test
    @DisplayName("@Column에서 name값이 있으면 해당 값을 필드로 사용한다.")
    void useAnnotationName() throws NoSuchFieldException {
        H2Column emailH2Column = new H2Column(personClass.getDeclaredField("name"));

        assertThat(emailH2Column.getColumnName()).isEqualTo("nick_name");
    }


    @Entity
    private static class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(nullable = false)
        private String email;
    }
}