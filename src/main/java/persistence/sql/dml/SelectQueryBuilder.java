package persistence.sql.dml;

import persistence.sql.domain.Person;

public class SelectQueryBuilder {
    public String findAll(Class<Person> personClass) {
        return "select id, nick_name, old, email FROM users";
    }
}
