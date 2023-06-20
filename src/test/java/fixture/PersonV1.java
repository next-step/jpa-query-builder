package fixture;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PersonV1 {
    @Id
    private Long id;
    private String name;
    private Integer age;
}
