package persistence.sql.cls;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "testClass")
public class ClassWithTableAndName {
    @Id
    private Long id;
}
