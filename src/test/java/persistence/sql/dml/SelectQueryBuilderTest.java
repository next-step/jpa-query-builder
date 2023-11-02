package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;
import test.PersonGenerator;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    Person person = PersonGenerator.getDefualtPerson();
    @Test
    @DisplayName("findAll 쿼리 테스트")
    void findAllTest() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        EntityMetaData entityMetaData = new EntityMetaData(person);
        String findAll = selectQueryBuilder.findAll(entityMetaData);
        assertThat(findAll).isEqualTo("select * from users");
    }

}