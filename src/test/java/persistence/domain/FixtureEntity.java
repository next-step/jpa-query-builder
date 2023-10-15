package persistence.domain;

import jakarta.persistence.*;


public class FixtureEntity {

    public static class WithoutId {
        private Long id;
    }

    @Entity
    public static class WithId {
        @Id
        private Long id;
    }

    @Entity
    public static class WithIdAndColumn {
        @Id
        @Column(name = "test_id")
        private Long id;
    }

    @Entity
    public static class IdWithGeneratedValue {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }

    @Entity
    public static class WithoutColumn {
        @Id
        private Long id;
        private String column;
    }

    @Entity
    public static class WithColumn {
        @Id
        private Long id;
        @Column(name = "test_column")
        private String column;
        @Column(nullable = false)
        private String notNullColumn;
    }

    @Entity
    public static class WithTransient {
        @Id
        private Long id;
        @Transient
        private String column;
    }

    @Entity
    public static class WithoutTableAnnotation {
        @Id
        private Long id;
    }

    @Entity
    @Table(name = "test_table")
    public static class WithTable {
        @Id
        private Long id;
    }

    public static class WithoutEntity {
    }

    @Entity
    public static class WithColumnLength {
        @Id
        private Long id;
        @Column(name = "column_length_hundred",length = 100)
        private String column;

        private String defaultLength;
    }

}