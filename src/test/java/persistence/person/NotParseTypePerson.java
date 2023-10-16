package persistence.person;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NotParseTypePerson {
    @Id
    private Long id;

    private Double name;
}
