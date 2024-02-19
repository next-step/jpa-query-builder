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

    @Test
    @DisplayName("필드에 맞춰서 필드의 sql이 생성된다./ annotationName, null")
    void generateSQL1() throws NoSuchFieldException {
        H2Column emailH2Column = new H2Column(personClass.getDeclaredField("email"));

        assertThat(emailH2Column.generateColumnSQL()).isEqualTo("email varchar(255) NOT NULL");
    }

    @Test
    @DisplayName("필드에 맞춰서 필드의 sql이 생성된다./ fieldName, not null")
    void generateSQL2() throws NoSuchFieldException {
        H2Column emailH2Column = new H2Column(personClass.getDeclaredField("name"));

        assertThat(emailH2Column.generateColumnSQL()).isEqualTo("nick_name varchar(255) NULL");
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