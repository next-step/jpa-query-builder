package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class DropDDLQueryBuilderTest {

    @DisplayName("DROP TABLE DDL Query 생성")
    @Test
    void build() {
        DropDDLQueryBuilder<Person> dropDDLQueryBuilder = new DropDDLQueryBuilder<>(Dialect.H2, Person.class);

        assertThat(dropDDLQueryBuilder.build())
                .isEqualTo("DROP TABLE USERS;");
    }
}
