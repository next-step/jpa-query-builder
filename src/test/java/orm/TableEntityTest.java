package orm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.exception.InvalidEntityException;

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
}


// 이 클래스에는 @Entity가 없다.
class DummyClass {

}

@Entity
@Table(name = "dummy_table")
class 테이블_애너테이션_있는_Entity {

    @Id
    private Long id;

}
