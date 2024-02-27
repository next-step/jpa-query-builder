package persistence.sql.dml.clause;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ValueClauseTest {

    @Test
    void getValueClause() {
        // given
        PersonForValueClauseTest person = new PersonForValueClauseTest("name", "email", 1);

        // when
        String result = ValueClause.getValueClause(person);

        // then
        assertThat(result).contains("name, email");
    }

    static class PersonForValueClauseTest {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(nullable = false)
        private String email;

        @Transient
        private Integer index;

        public PersonForValueClauseTest(String name, String email, Integer index) {
            this.name = name;
            this.email = email;
            this.index = index;
        }
    }
}