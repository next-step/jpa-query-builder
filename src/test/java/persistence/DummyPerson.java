package persistence;

import domain.Person;

public class DummyPerson {

    private DummyPerson() {
    }

    public static Person of() {
        return new Person(1L, "name", 10, "a@a.com", 10);
    }

    public static Person ofNullId() {
        return new Person("name", 10, "a@a.com");
    }

}
