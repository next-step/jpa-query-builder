package persistence.sql.cls;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClassWithoutTable {

    @Id
    private Long id;
}