package domain;

public final class PersonFixture {
    private PersonFixture() {}

    public static Person createPerson() {
        return new Person()
                .setId(1L)
                .setName("고정완")
                .setAge(30)
                .setEmail("ghojeong@email.com")
                .setIndex(2);
    }
}
