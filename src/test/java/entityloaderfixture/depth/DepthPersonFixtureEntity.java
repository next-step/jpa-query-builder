package entityloaderfixture.depth;

import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DepthPersonFixtureEntity that = (DepthPersonFixtureEntity) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
