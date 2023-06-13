package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dialect.H2DbDialect;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    @DisplayName("DDL 쿼리 생성 테스트")
    @Test
    void ddlQueryBuildTest() {

        final JavaToSqlColumnParser columnParser = new JavaToSqlColumnParser(new H2DbDialect());
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(columnParser);
        final String sql = ddlQueryBuilder.build(Person.class);

        assertThat(ddlQueryBuilder.getIdColumns().keySet()).containsExactly("id");
        assertThat(ddlQueryBuilder.getIdColumns().values()).containsExactly("bigint auto_increment");
        assertThat(ddlQueryBuilder.getColumns().keySet()).containsExactly("nick_name", "old", "email");
        assertThat(ddlQueryBuilder.getColumns().values()).containsExactly("varchar(255) null", "int null", "varchar(255) not null");
        assertThat(sql)
                .isEqualTo("create table users (id bigint auto_increment,nick_name varchar(255) null,old int null,email varchar(255) not null, constraint pk_person primary key (id));");
    }

}
