package persistence.sql.ddl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DdlGeneratorTest {
    @Test
    @DisplayName("create 쿼리 생성 테스트")
    void generateCreateDdlTest() {
        final DBColumnTypeMapper columnTypeMapper = new DefaultDBColumnTypeMapper();
        final DdlGenerator generator = new DdlGenerator(columnTypeMapper);
        final String query = generator.generateCreateDdl(Person.class);
        assertThat(query).isEqualToIgnoringCase("create table users (id bigint not null auto_increment,nick_name varchar,old int,email varchar not null,CONSTRAINT PK_Users PRIMARY KEY (id))");
    }
}