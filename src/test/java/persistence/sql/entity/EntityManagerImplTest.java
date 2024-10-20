package persistence.sql.entity;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import persistence.sql.Dialect;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.*;
import persistence.sql.model.EntityColumnValue;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcServerTest
class EntityManagerImplTest {

    private static final Dialect dialect = new H2Dialect();
    private static final JdbcTemplate jdbcTemplate = JdbcServerExtension.getJdbcTemplate();
    private static final EntityManager entityManager = new EntityManagerImpl(jdbcTemplate);

    @BeforeAll
    static void init() {
        QueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class, dialect);
        jdbcTemplate.execute(createQueryBuilder.build());
    }

    @Test
    void 데이터_삽입_및_조회() throws NoSuchFieldException {
        String name = "이름";
        int age = 10;
        String email = "jsss@test.com";
        int index = 1;
        Person person = new Person(name, age, email, index);
        entityManager.persist(person);

        Person savedPerson = entityManager.find(Person.class, 1L);
        Field nameField = savedPerson.getClass().getDeclaredField("name");
        Field ageField = savedPerson.getClass().getDeclaredField("age");
        Field emailField = savedPerson.getClass().getDeclaredField("email");

        EntityColumnValue entityColumnValue = new EntityColumnValue(nameField, savedPerson);
        EntityColumnValue ageColumnValue = new EntityColumnValue(ageField, savedPerson);
        EntityColumnValue emailColumnValue = new EntityColumnValue(emailField, savedPerson);

        assertThat(entityColumnValue.getValue()).isEqualTo(name);
    }

    @Test
    void 데이터_수정() throws NoSuchFieldException {
        String name = "이름";
        int age = 11;
        String email = "jsss@test.co1m";
        int index = 1;
        Person person = new Person(name, age, email, index);
        entityManager.persist(person);

        Person insertedPerson = entityManager.find(Person.class, 1L);
        String updateEmail = "test@naver.com";
        insertedPerson.setEmail(updateEmail);
        entityManager.update(insertedPerson);


        Field emailField = insertedPerson.getClass().getDeclaredField("email");
        EntityColumnValue emailColumnValue = new EntityColumnValue(emailField, insertedPerson);

//        assertThat(emailColumnValue.getValue()).isEqualTo(updateEmail);
    }
}
