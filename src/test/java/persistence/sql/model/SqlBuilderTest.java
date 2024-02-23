package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SqlBuilderTest {
    private final Sql.Builder sqlbuilder = new Sql.Builder(new H2Dialect());

    @Test
    @DisplayName("create query 생성하기")
    void buildCreateQuery() {
        Column name = new Column(SqlType.VARCHAR, "name", List.of());

        String result = sqlbuilder
                .create()
                .and()
                .table("test")
                .and()
                .leftParenthesis()
                .columns(List.of(name))
                .rightParenthesis()
                .build();

        assertThat(result).isEqualTo("CREATE TABLE test (name varchar);");
    }

    @Test
    @DisplayName("drop query 생성하기")
    void buildDropQuery() {
        String result = sqlbuilder
                .drop()
                .and()
                .table("test")
                .build();

        assertThat(result).isEqualTo("DROP TABLE test;");
    }
}
