package H2QueryBuilder.fixtures;

import jakarta.persistence.*;
import persistence.sql.dml.Person;

public class BuilderDMLFixtures {
    public static Person 완벽한_사람_객체(Long id, String name, int age, String email, int index) {
        return new Person(id, name, age, email, index);
    }

    public static Person 이름이_없는_사람_객체(Long id, int age, String email, int index) {
        return new Person(id, age, email, index);
    }
}
