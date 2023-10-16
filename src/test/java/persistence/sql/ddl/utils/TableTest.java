package persistence.sql.ddl.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

class TableTest {

    @Test
    @DisplayName("테이블 이름을 가져온다.")
    void getNameTest() {
        Table table = new Table(Person.class);
        String name = table.getName();
        Assertions.assertThat(name).isEqualToIgnoringCase("person");
    }
}