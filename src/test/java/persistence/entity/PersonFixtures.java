package persistence.entity;

import persistence.testutils.ReflectionTestSupport;

public class PersonFixtures {

    public static Person fixture(long id, String name, int age, String email) {
        Person person = new Person();
        ReflectionTestSupport.setFieldValue(person, "id", id);
        ReflectionTestSupport.setFieldValue(person, "name", name);
        ReflectionTestSupport.setFieldValue(person, "age", age);
        ReflectionTestSupport.setFieldValue(person, "email", email);
        return person;
    }

    public static Person fixtureById(long id) {
        Person person = new Person();
        ReflectionTestSupport.setFieldValue(person, "id", id);
        return person;
    }
}
