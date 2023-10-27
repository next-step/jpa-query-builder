package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;

import static org.junit.jupiter.api.Assertions.*;

class InsertQueryBuilderTest {


    @Test
    @DisplayName("insert 쿼리를 생성한다.")
    void insertTest() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
        EntityMetaData entityMetaData = new EntityMetaData(Person.class);
        Person person = new Person();
        person.setAge(28);
        person.setName("지영");
        person.setEmail("jy@lim.com");
        Assertions.assertThat(insertQueryBuilder.create(entityMetaData, person))
                .isEqualTo("insert into users (old, nick_name, email) values(28, '지영', jy@lim.com)");
    }

    @Test
    @DisplayName("value 생성 Test")
    void valueGenerate() {

    }
}