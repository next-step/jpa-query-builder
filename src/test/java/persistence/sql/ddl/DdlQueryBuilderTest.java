package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.collection.DbDialectMap;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    @DisplayName("DDL 쿼리 생성 테스트")
    @Test
    void ddlQueryBuildTest() {
        final DefaultJavaToSqlColumnParser columnParser = new DefaultJavaToSqlColumnParser(new DbDialectMap());
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(columnParser);
        ddlQueryBuilder = ddlQueryBuilder.create(Person.class);

        final String sql = ddlQueryBuilder.build();
        System.out.println(sql);

        assertThat(ddlQueryBuilder.getIdColumns().keySet()).containsExactly("id");
        assertThat(ddlQueryBuilder.getIdColumns().values()).containsExactly("bigint auto_increment");
        assertThat(ddlQueryBuilder.getColumns().keySet()).containsExactly("nick_name", "old", "email");
        assertThat(ddlQueryBuilder.getColumns().values()).containsExactly("varchar(255) null", "int(3) null", "varchar(255) not null");
        assertThat(ddlQueryBuilder.build())
                .isEqualTo("create table person (id bigint auto_increment,nick_name varchar(255) null,old int(3) null,email varchar(255) not null, constraint pk_person primary key (id));");
    }

}
