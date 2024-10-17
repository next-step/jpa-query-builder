package H2QueryBuilder.fixtures;

import jakarta.persistence.*;

public class BuilderDDLFixtures {
    public static Class<?> 아이디만_존재하는_클래스() {
        @Entity
        class Person {
            @Id
            private Long id;
        }
        return Person.class;
    }

    public static Class<?> 컬럼어노테이션_존재하지않는_클래스() {
        @Entity
        class Person {
            @Id
            private Long id;

            private String name;

            private Integer age;

            private String email;
        }
        return Person.class;
    }

    public static Class<?> 모든어노테이션_존재하는_클래스() {
        @Table(name = "users")
        @Entity
        class Person {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(name = "nick_name")
            private String name;

            @Column(name = "old")
            private Integer age;

            @Column(nullable = false)
            private String email;

        }
        return Person.class;
    }

    public static Class<?> 타입을_지원하지않는_클래스() {
        @Table(name = "users")
        @Entity
        class Person {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(name = "nick_name")
            private String name;

            @Column(name = "old")
            private Integer age;

            @Column(nullable = false)
            private String email;

            private Double cash;

        }
        return Person.class;
    }

}
