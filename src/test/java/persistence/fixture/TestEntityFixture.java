package persistence.fixture;

import jakarta.persistence.*;

public class TestEntityFixture {
    @Entity
    @Table(name = "entity_name")
    public static class EntityWithValidAnnotation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @Column(name = "name", length = 200)
        String name;
        @Column(name = "old")
        Integer age;

        public EntityWithValidAnnotation() {
        }

        public EntityWithValidAnnotation(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public EntityWithValidAnnotation(long id, String nickName, int age) {
            this.id = id;
            this.name = nickName;
            this.age = age;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }
    }

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

        public EntityWithOutEntityAnnotation(long id, String nickName, int age) {
            this.id = id;
            this.name = nickName;
            this.age = age;
        }
    }
}
