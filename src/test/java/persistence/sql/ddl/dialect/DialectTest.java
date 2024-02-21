package persistence.sql.ddl.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.LegacyPerson;
import persistence.entity.Person;
import persistence.sql.ddl.dialect.h2.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class DialectTest {

    @DisplayName("Person 만드는 테이블 쿼리문을 반환한다.")
    @Test
    void getCreateTableSql() {
        Dialect dialect = new H2Dialect();

        String sql = dialect.createTable(LegacyPerson.class);
        final String expected = "CREATE TABLE LegacyPerson(\n" +
                "id BIGINT PRIMARY KEY,\n" +
                "name VARCHAR,\n" +
                "age INTEGER\n" +
                ");";

        assertThat(sql).isEqualTo(expected);
    }

    @DisplayName("Column이 추가된 테이블 쿼리문을 반환다.")
    @Test
    void isColumnCreateTableSql() {
        Dialect dialect = new H2Dialect();

        String sql = dialect.createTable(Person.class);
        final String expected = "CREATE TABLE Person(\n" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "nick_name VARCHAR,\n" +
                "old INTEGER,\n" +
                "email VARCHAR  NOT NULL\n" +
                ");";

        assertThat(sql).isEqualTo(expected);
    }
    
}