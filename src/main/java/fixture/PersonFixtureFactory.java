package fixture;

import domain.Person;

import java.util.Arrays;
import java.util.List;

public class PersonFixtureFactory {

    public static Person getFixture() {
        return new Person("테스트0", 20, "000@domain.com", 0);
    }

    public static List<Person> getFixtures() {
        return Arrays.asList(
                new Person("테스트1", 21, "111@domain.com", 1),
                new Person("테스트2", 22, "222@domain.com", 2),
                new Person("테스트3", 23, "333@domain.com", 3),
                new Person("테스트4", 24, "444@domain.com", 4),
                new Person("테스트5", 25, "555@domain.com", 5)
        );
    }
}
