package persistence.fixture;

import jakarta.persistence.*;

public class TestEntityFixture {
    @Entity
    public static class EntityWithMultiIdAnnotation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @Id
        @Column(name = "name", length = 200)
        String name;
        @Column(name = "age")
        Integer age;

        public EntityWithMultiIdAnnotation() {

        }

        public EntityWithMultiIdAnnotation(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }

    public static class EntityWithOutEntityAnnotation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @Id
        @Column(name = "name", length = 200)
        String name;
        @Column(name = "age")
        Integer age;

        public EntityWithOutEntityAnnotation() {
        }

        public EntityWithOutEntityAnnotation(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }
}
