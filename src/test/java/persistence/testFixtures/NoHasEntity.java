package persistence.testFixtures;

import jakarta.persistence.Id;

public class NoHasEntity {

    @Id
    private Long id;
    private String name;
    private Integer age;
}
