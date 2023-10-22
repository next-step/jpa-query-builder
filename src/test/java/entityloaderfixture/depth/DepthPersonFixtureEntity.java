package entityloaderfixture.depth;

import jakarta.persistence.*;

@Table(name = "depth_person_fixture")
@Entity
public class DepthPersonFixtureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int age;

    protected DepthPersonFixtureEntity() {
    }

    public DepthPersonFixtureEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
