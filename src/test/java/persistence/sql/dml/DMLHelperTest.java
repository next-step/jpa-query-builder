package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.common.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DMLHelperTest {

    @Test
    void Insert_쿼리를_생성한다() {
        Person person = new Person(10L, "홍길동", 10, "abc@abc.com", 0);
        DMLHelper dmlHelper = new DMLHelper();

        assertEquals(
            "insert into users (nick_name, old, email) values ('홍길동', 10, 'abc@abc.com');",
            dmlHelper.getInsertQuery(Person.class, person)
        );
    }

    @Test
    void FindAllQuery를_생성한다() {
        DMLHelper dmlHelper = new DMLHelper();

        assertEquals(
            "select * from users;",
            dmlHelper.getFindAllQuery(Person.class)
        );
    }

    @Test
    void FindByIdQuery를_생성한다() {
        DMLHelper dmlHelper = new DMLHelper();
        Long id = 1L;

        assertEquals(
                "select * from users where id = 1;",
                dmlHelper.getFindByIdQuery(Person.class, id)
        );
    }

    @Test
    void Delete쿼리를_생성한다() {
        DMLHelper dmlHelper = new DMLHelper();
        var person = new Person("홍길동", 30, "test@test.com", 10);

        assertEquals(
                "delete from users where nick_name = '홍길동' and old = 30 and email = 'test@test.com';",
                dmlHelper.getDeleteQuery(Person.class, person)
        );
    }
}