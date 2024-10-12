package orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.exception.InvalidEntityException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TableFieldTest {

    @Test
    @DisplayName("테이블의 필드들은 @Transient을 제외하고 등록된다.")
    void transient_테스트() {

        // given
        TableEntity<DummyEntity> tableEntity = new TableEntity<>(DummyEntity.class);

        // when
        List<TableField> allFields = tableEntity.getAllFields();

        // then
        assertThat(allFields).hasSize(3);
    }

    @Test
    @DisplayName("하나의 필드에 @Column 과 @Transient이 동시에 사용 될 수 없다.")
    void transient_테스트_2() {

        // given
        Class<InvalidDummyEntity> invalidEntity = InvalidDummyEntity.class;

        // when & then
        assertThatThrownBy(() -> new TableEntity<>(invalidEntity))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessageContaining("@Transient & @Column cannot be used in same field");
    }

}

@Entity
class DummyEntity {

    @Id
    private Long id;

    @Column
    private Integer fieldName;

    @Column(name = "annotated_name")
    private String fieldName2;

    @Transient
    private String thisIdNotField;
}

@Entity
class InvalidDummyEntity {

    @Id
    private Long id;

    @Column
    @Transient
    private String thisIdNotField2; // 사용
}
