package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2QueryMapperTest {

    @Test
    @DisplayName("기본적인 create 쿼리 만들기")
    public void create_ddl() {
        H2QueryMapper h2QueryMapper = new H2QueryMapper();
        String query = h2QueryMapper.createQuery(Person.class);

        assertThat(query).isEqualToIgnoringCase("create table person (id bigint primary key, name varchar(255), age integer)");
    }
}