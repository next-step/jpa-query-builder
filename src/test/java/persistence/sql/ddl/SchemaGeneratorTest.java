package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SchemaGeneratorTest {

    private final SchemaGenerator creator = new SchemaGenerator(Person.class);

    @DisplayName("특정 엔티티 클래스의 create table 쿼리를 생성한다.")
    @Test
    void create() {
        String ddl = creator.generateCreateTableDdlString();
        assertThat(ddl).isEqualTo("CREATE TABLE users (id bigint not null auto_increment, nick_name varchar(255), old integer, email varchar(255) not null, primary key (id))");
    }

    @DisplayName("특정 엔티티 클래스의 drop table 쿼리를 생성한다.")
    @Test
    void createDropTableDdlString() {
        assertThat(creator.generateDropTableDdlString()).isEqualTo("DROP TABLE IF EXISTS users");
    }
}
