package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DDLGeneratorTest {

    @Test
    void createPersonCreateQuery() {
        // given
        DDLGenerator ddlGenerator = new DDLGenerator(Person.class);

        // when
        String createSql = ddlGenerator.generateCreate();

        // then
        assertThat(createSql).isEqualTo("CREATE TABLE users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY not null, " +
                "nick_name VARCHAR(255) null, " +
                "old INT null, " +
                "email VARCHAR(255) not null);");
    }

    @Test
    void createPersonDropQuery() {
        // given
        DDLGenerator ddlGenerator = new DDLGenerator(Person.class);

        // when
        String dropSql = ddlGenerator.generateDrop();

        // then
        assertThat(dropSql).isEqualTo("DROP TABLE users;");
    }
}
