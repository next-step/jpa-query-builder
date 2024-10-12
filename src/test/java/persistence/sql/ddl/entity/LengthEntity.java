package persistence.sql.ddl.entity;

import jakarta.persistence.*;

@Entity
public class LengthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String address;
}
