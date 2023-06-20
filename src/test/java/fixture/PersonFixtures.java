package fixture;

public class PersonFixtures {
    public static PersonV3 createPerson() {
        return new PersonV3(
                1L,
                "yohan",
                31,
                "yohan@google.com",
                1
        );
    }
}
