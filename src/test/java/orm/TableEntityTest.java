package orm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.exception.EntityHasNoDefaultConstructorException;
import orm.exception.InvalidEntityException;
import test_entity.기본생성자_없는_Entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TableEntityTest {

    @Test
    @DisplayName("@Entity가 아닌 클래스는 TableEntity가 될 수 없다.")
    void 엔티티_검증() {

        // given
        Class<DummyClass> tableClass = DummyClass.class;

        // when && then
        assertThatThrownBy(() -> new TableEntity<>(tableClass))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessageContaining("is not an entity");
    }

    @Test
    @DisplayName("@Table 에너테이션이 있으면, 클래스명이 아닌 @Table으로 테이블명을 가져온다.")
    void 테이블_검증() {

        // given
        Class<테이블_애너테이션_있는_Entity> tableClass = 테이블_애너테이션_있는_Entity.class;
        var tableEntity = new TableEntity<>(tableClass);

        // when && then
        String tableName = tableEntity.getTableName();

        // then
        assertThat(tableName).isEqualTo("dummy_table");
    }

    @Test
    @DisplayName("기본 생성자가 없는 엔티티는 클래스 리터럴로 TableEntity를 만들 수 없다.")
    void 기본생성자_검증() {
        // 기본생성자를 통해 DDL을 위한 엔티티 클래스를 만들기 때문에 강제한다. JPA도 비슷한것같다.

        // given
        Class<기본생성자_없는_Entity> entityClass = 기본생성자_없는_Entity.class;

        // when && then
        assertThatThrownBy(() -> new TableEntity<>(entityClass))
                .isInstanceOf(EntityHasNoDefaultConstructorException.class);
    }
}

// 이 클래스에는 @Entity가 없다.
class DummyClass {

}

@Entity
@Table(name = "dummy_table")
class 테이블_애너테이션_있는_Entity {

    @Id
    private Long id;

    public 테이블_애너테이션_있는_Entity() {
    }

    public 테이블_애너테이션_있는_Entity(Long id) {
        this.id = id;
    }
}
