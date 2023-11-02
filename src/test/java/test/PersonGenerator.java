package test;

import persistence.sql.ddl.Person;

public class PersonGenerator {

    public static Person getDefualtPerson() {
        Person person = new Person();
        person.setAge(28);
        person.setName("지영");
        person.setEmail("jy@lim.com");

        return person;
    }
}
