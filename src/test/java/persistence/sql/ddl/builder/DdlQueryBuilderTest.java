package persistence.sql.ddl.builder;

import fixture.PersonV1;
import fixture.PersonV2;
import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    @Test
    @DisplayName("기본적인 create 쿼리 만들기")
    public void create_ddl() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(PersonV1.class);

        assertThat(ddlQueryBuilder.create()).isEqualToIgnoringCase(
                "create table personv1 " +
                        "(id bigint primary key, name varchar(255), age integer)"
        );
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 create 쿼리 만들기 (@GeneratedValue, @Column)")
    public void created_ddl_2() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(PersonV2.class);

        assertThat(ddlQueryBuilder.create()).isEqualToIgnoringCase(
                "create table personv2 " +
                        "(id bigint primary key auto_increment, nick_name varchar(255), old integer, email varchar(255) not null)");
    }

    @Test
    @DisplayName("추가된 어노테이션을 반영하여 create 쿼리 만들기 (@Transient)")
    public void created_ddl_3() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(PersonV3.class);

        assertThat(ddlQueryBuilder.create()).isEqualToIgnoringCase(
                "create table users " +
                        "(id bigint primary key auto_increment, nick_name varchar(255), old integer, email varchar(255) not null)"
        );
    }

    @Test
    @DisplayName("테이블 정보를 바탕으로 drop 쿼리 만들어보기 ")
    public void drop() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(PersonV3.class);

        assertThat(ddlQueryBuilder.drop()).isEqualToIgnoringCase("drop table users");
    }
}