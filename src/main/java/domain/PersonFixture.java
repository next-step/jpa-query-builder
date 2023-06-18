package domain;

public final class PersonFixture {
    private PersonFixture() {}

    public static Person createPerson() {
        return new Person(
                1L,
                "고정완",
                30,
                "ghojeong@email.com",
                2
        );
    }
}
