package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.h2.H2Dialect;
import persistence.sql.ddl.h2.H2GeneratedValueStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class TableQueryMapperTest {
    private final TableQueryMapper tableQueryMapper =
            new TableQueryMapper(new ColumnMapper(new H2Dialect()), new H2GeneratedValueStrategy());

    @Test
    @DisplayName("기본적인 create 쿼리 만들기")
    public void create_ddl() {
        String query = tableQueryMapper.create(Person.class);

        assertThat(query).isEqualToIgnoringCase("create table person " +
                "(id bigint primary key, name varchar(255), age integer)");
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 create 쿼리 만들기 (@GeneratedValue, @Column)")
    public void created_ddl_2() {
        String query = tableQueryMapper.create(PersonV2.class);

        assertThat(query).isEqualToIgnoringCase(
                "create table personv2 " +
                        "(id bigint primary key auto_increment, nick_name varchar(255), old integer, email varchar(255) not null)");
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 create 쿼리 만들기 (@GeneratedValue, @Column")
    public void created_ddl_3() {
        String query = tableQueryMapper.create(PersonV3.class);

        assertThat(query).isEqualToIgnoringCase("create table users (id bigint primary key auto_increment, nick_name varchar(255), old integer, email varchar(255) not null)");
    }

    @Test
    @DisplayName("테이블 정보를 바탕으로 drop 쿼리 만들어보기 ")
    public void drop() {
        String query = tableQueryMapper.drop(PersonV3.class);

        assertThat(query).isEqualToIgnoringCase("drop table users");
    }
}