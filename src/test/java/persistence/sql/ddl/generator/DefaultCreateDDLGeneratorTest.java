package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Table;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.ddl.entity.LengthEntity;
import persistence.sql.ddl.entity.NotSupportStratgyEntity;
import persistence.sql.ddl.exception.NotSupportException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DefaultCreateDDLGeneratorTest {
    @Test
    void DDL을_생성한다() {
        Table table = Table.from(Person.class);
        H2Dialect h2Dialect = new H2Dialect();
        DefaultCreateDDLGenerator h2Creator = new DefaultCreateDDLGenerator(h2Dialect);

        String ddl = h2Creator.generate(table);

        assertThat(ddl).isEqualTo("CREATE TABLE users (id BIGINT generated by default as identity, nick_name VARCHAR(255) , old INTEGER , email VARCHAR(255) not null);");
    }

    @Test
    void 컬럼의_길이가_지정된_DDL을_생성한다() {
        Table table = Table.from(LengthEntity.class);
        H2Dialect h2Dialect = new H2Dialect();
        DefaultCreateDDLGenerator h2Creator = new DefaultCreateDDLGenerator(h2Dialect);

        String ddl = h2Creator.generate(table);

        assertThat(ddl).isEqualTo("CREATE TABLE LengthEntity (id BIGINT generated by default as identity, address VARCHAR(10) );");
    }

    @Test
    void 제공되지_않는_전략을_사용시_실패한다() {
        Table table = Table.from(NotSupportStratgyEntity.class);
        H2Dialect h2Dialect = new H2Dialect();
        DefaultCreateDDLGenerator h2Creator = new DefaultCreateDDLGenerator(h2Dialect);

        assertThatExceptionOfType(NotSupportException.class)
            .isThrownBy(() -> h2Creator.generate(table));
    }
}
