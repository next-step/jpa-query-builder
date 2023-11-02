package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;

class InsertQueryBuilderTest {


    @Test
    @DisplayName("insert 쿼리를 생성한다.")
    void insertTest() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
        Person person = new Person();
        person.setAge(28);
        person.setName("지영");
        person.setEmail("jy@lim.com");
        EntityMetaData entityMetaData = new EntityMetaData(person);

        Assertions.assertThat(insertQueryBuilder.create(entityMetaData))
                .isEqualTo("insert into users (old, nick_name, email) values(28, '지영', 'jy@lim.com')");
    }

    @Test
    @DisplayName("value 생성 Test")
    void valueGenerate() {

    }
}