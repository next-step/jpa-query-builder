package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2QueryMapperTest {

    @Test
    @DisplayName("기본적인 create 쿼리 만들기")
    public void create_ddl() {
        H2QueryMapper h2QueryMapper = new H2QueryMapper();
        String query = h2QueryMapper.createQuery(PersonV3.class);

        assertThat(query).isEqualToIgnoringCase("create table person " +
                "(id bigint primary key, name varchar(255), age integer)");
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 create 쿼리 만들기 (@GeneratedValue, @Column)")
    public void created_ddl_2() {
        H2QueryMapper h2QueryMapper = new H2QueryMapper();
        String query = h2QueryMapper.createQuery(PersonV2.class);

        assertThat(query).isEqualToIgnoringCase(
                "create table personv2 " +
                        "(id bigint primary key auto_increment, nick_name varchar(255), old integer(3), email varchar(255) not null)");
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 create 쿼리 만들기 (@GeneratedValue, @Column")
    public void created_ddl_3() {
        H2QueryMapper h2QueryMapper = new H2QueryMapper();
        String query = h2QueryMapper.createQuery(PersonV3.class);

        assertThat(query).isEqualToIgnoringCase("create table users (id bigint primary key auto_increment, nick_name varchar(255), old integer(3), email varchar(255) not null)");
    }
}