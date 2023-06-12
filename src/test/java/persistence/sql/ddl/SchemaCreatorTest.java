package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SchemaCreatorTest {

    @DisplayName("특정 엔티티 클래스의 DDL을 생성한다.")
    @Test
    void create() {
        SchemaCreator creator = new SchemaCreator(Person.class);
        String ddl = creator.create();
        assertThat(ddl).isEqualTo("CREATE TABLE Person (id bigint not null, name text, age integer, primary key (id))");
    }
}