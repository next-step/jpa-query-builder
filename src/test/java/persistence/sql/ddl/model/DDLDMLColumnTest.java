package persistence.sql.ddl.model;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.converter.H2TypeConverter;
import persistence.sql.ddl.mapping.H2PrimaryKeyGenerationType;

import static org.assertj.core.api.Assertions.assertThat;

class DDLDMLColumnTest {
    private DDLColumn column;

    @BeforeEach
    void setUp() {
        column = new DDLColumn(
                new H2TypeConverter(),
                new H2PrimaryKeyGenerationType()
        );
    }

    @Test
    void columnCreateQueryTest() {
        final var expected = "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL";

        final var actual = column.create(Person.class);

        assertThat(actual).isEqualTo(expected);
    }

}
