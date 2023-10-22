package persistence.fixture;

import jakarta.persistence.*;

public class TestEntityFixture {
    @Entity
    @Table(name = "entity_name")
    public static class SampleOneWithValidAnnotation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @Column(name = "name", length = 200)
        String name;
        @Column(name = "old")
        Integer age;

        public SampleOneWithValidAnnotation() {
        }

        public SampleOneWithValidAnnotation(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public SampleOneWithValidAnnotation(long id, String nickName, int age) {
            this.id = id;
            this.name = nickName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "SampleOneWithValidAnnotation{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Entity
    @Table(name = "two")
    public static class SampleTwoWithValidAnnotation {
        @Id
        @Column(name = "two_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @Column(name = "nick_name", length = 200, nullable = false)
        String name;
        @Column
        Integer age;

        public SampleTwoWithValidAnnotation() {

        }

        public SampleTwoWithValidAnnotation(long id, String nickName, int age) {
            this.id = id;
            this.name = nickName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "SampleTwoWithValidAnnotation{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
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
