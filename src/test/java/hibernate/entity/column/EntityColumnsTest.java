package hibernate.entity.column;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnsTest {

    @Test
    void Transient가_없는_필드로_생성한다() {
        EntityColumns actual = new EntityColumns(TestEntity.class.getDeclaredFields());
        assertThat(actual.getValues()).hasSize(2);
    }

    static class TestEntity {
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Transient
        private String email;
    }
}
