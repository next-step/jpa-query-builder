package persistence.sql.cls;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class ClassWithTableButEmptyName {
    @Id
    private Long id;
}
