package persistence.sql.ddl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.core.EntityMetadata;

import static org.assertj.core.api.Assertions.assertThat;

class DdlGeneratorTest {
    DBColumnTypeMapper columnTypeMapper;
    DdlGenerator generator;
    EntityMetadata<Person> personEntityMetadata;

    @BeforeEach
    void setUp() {
        columnTypeMapper = DefaultDBColumnTypeMapper.getInstance();
        generator = new DdlGenerator(columnTypeMapper);
        personEntityMetadata = new EntityMetadata<>(Person.class);
    }

    @Test
    @DisplayName("create 쿼리 생성 테스트")
    void generateCreateDdlTest() {
        final String query = generator.generateCreateDdl(personEntityMetadata);

        assertThat(query).isEqualToIgnoringCase("create table users (id bigint not null auto_increment,nick_name varchar,old int,email varchar not null,CONSTRAINT PK_Users PRIMARY KEY (id))");
    }

    @Test
    @DisplayName("drop 쿼리 생성 테스트")
    void generateDropDdlTest() {
        final String query = generator.generateDropDdl(personEntityMetadata);

        assertThat(query).isEqualToIgnoringCase("drop table users");
    }
}
