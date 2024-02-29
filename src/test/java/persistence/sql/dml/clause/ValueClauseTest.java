package persistence.sql.dml.clause;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ValueClauseTest {

    @Test
    void getValueClause() {
        // given
        PersonForValueClauseTest person = new PersonForValueClauseTest("name", 26, "email", 1);

        // when
        String result = ValueClause.getValueClause(person);

        // then
        assertThat(result).contains("'name', 26, 'email'");
    }

    static class PersonForValueClauseTest {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private final String name;

        private final int age;

        @Column(nullable = false)
        private final String email;

        @Transient
        private final Integer index;

        public PersonForValueClauseTest(String name, int age, String email, Integer index) {
            this.name = name;
            this.age = age;
            this.email = email;
            this.index = index;
        }
    }
}
