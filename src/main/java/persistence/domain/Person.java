    package persistence.domain;

    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.Table;
    import jakarta.persistence.Transient;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    @Table(name = "users")
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public class Person {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(name = "old")
        private Integer age;

        @Column(nullable = false)
        private String email;

        @Transient
        private Integer index;

    }
