package test_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dummy_table")
public class 기본생성자_없는_Entity {

    @Id
    private Long id;

    public 기본생성자_없는_Entity(Long id) {
        this.id = id;
    }
}
