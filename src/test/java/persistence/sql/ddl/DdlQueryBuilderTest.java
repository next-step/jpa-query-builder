package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    @Test
    void createDdl() {
        String expectedQuery = "create table users (id bigint not null auto_increment, nick_name varchar(255), old integer, email varchar(255) not null, primary key (id));";
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        String createDdl = ddlQueryBuilder.buildCreateQuery(Person.class);

        assertThat(createDdl).isEqualTo(expectedQuery);
    }

    @Test
    void dropDdl() {
        String expectedQuery = "drop table users;";
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        String dropDdl = ddlQueryBuilder.buildDropQuery(Person.class);

        assertThat(dropDdl).isEqualTo(expectedQuery);
    }
}
