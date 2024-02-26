package persistence.sql.domain;

import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InsertTest {

    @Test
    void should_create_insert_clause_when_id_does_not_exist() {
        Person person = new Person("cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);

        Insert insert = new Insert(table);

        assertAll(
                ()-> assertThat(insert.getColumnClause()).isEqualTo("nick_name,old,email"),
                ()-> assertThat(insert.getValueClause()).isEqualTo("'cs',29,'katd216@gmail.com'")
        );
    }
    @Test
    void should_create_insert_clause_when_id_exist() {
        Person person = new Person(1l,"cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);

        Insert insert = new Insert(table);

        assertAll(
                ()-> assertThat(insert.getColumnClause()).isEqualTo("id,nick_name,old,email"),
                ()-> assertThat(insert.getValueClause()).isEqualTo("1,'cs',29,'katd216@gmail.com'")
        );
    }


}
