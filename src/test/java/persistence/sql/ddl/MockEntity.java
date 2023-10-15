package persistence.sql.ddl;

import jakarta.persistence.*;


public class MockEntity {

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

}