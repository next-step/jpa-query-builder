package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DDLGeneratorTest {

    @Test
    void createPersonCreateQuery() {
        // given
        DDLGenerator ddlGenerator = new DDLGenerator();

        // when
        String createSql = ddlGenerator.generateCreate(Person.class);

        // then
        assertThat(createSql).isEqualTo("CREATE TABLE person (" +
                "id BIGINT, " +
                "name VARCHAR, " +
                "age INT " +
                "CONSTRAINT person_pk PRIMARY KEY(id));");
    }

}