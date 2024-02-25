package persistence.sql.domain;

import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseTableTest {

    @Test
    void should_create_column_cause_when_id_does_not_exist() {
        Person person = new Person("cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);
        String columnClause = table.columnClause();

        assertThat(columnClause).isEqualTo("nick_name,old,email");
    }

    @Test
    void should_create_column_cause_when_id_exist() {
        Person person = new Person(1l, "cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);
        String columnClause = table.columnClause();

        assertThat(columnClause).isEqualTo("id,nick_name,old,email");
    }

    @Test
    void should_create_value_cause_when_id_does_not_exist() {
        Person person = new Person("cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);
        String valueClause = table.valueClause();


        assertThat(valueClause).isEqualTo("'cs',29,'katd216@gmail.com'");
    }

    @Test
    void should_create_value_cause_when_id_exist() {
        Person person = new Person(1l, "cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);
        String valueClause = table.valueClause();


        assertThat(valueClause).isEqualTo("1,'cs',29,'katd216@gmail.com'");
    }

    @Test
    void should_return_id_column_name() {
        DatabaseTable table = new DatabaseTable(Person.class);

        String idColumnName = table.getIdColumnName();

        assertThat(idColumnName).isEqualTo("id");
    }

    @Test
    void should_return_where_clause() {
        Person person = new Person(1l, "cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);

        String whereClause = table.whereClause();

        assertThat(whereClause).isEqualTo("id=1 and nick_name='cs' and old=29 and email='katd216@gmail.com'");
    }
}
