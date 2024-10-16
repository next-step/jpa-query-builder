package orm;

import jakarta.persistence.*;
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

    @Test
    @DisplayName("데이터가 들어있는 엔티티로 TableEntit y를 만들면 하위 TableField 들이 값을 가지고 있다.")
    void 테이블_필드_데이터존재_검증() {

        // given
        Person person = new Person(1, 1L, "name"); // 모든 필드에 데이터가 있음
        var tableEntity = new TableEntity<>(person);

        // when
        List<TableField> allFields = tableEntity.getAllFields();

        // then
        assertThat(allFields).allSatisfy(e -> {
            assertThat(e.getFieldValue()).isNotNull();
        });
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

    public void setFieldName2(String fieldName2) {
        this.fieldName2 = fieldName2;
    }
}

@Entity
class InvalidDummyEntity {

    @Id
    private Long id;

    @Column
    @Transient
    private String thisIdNotField2; // 사용
}

@Entity
@Table(name = "person")
class Person {

    @Id
    private Long id;

    private String name;

    private int age;

    public Person() {
    }

    public Person(int age, Long id, String name) {
        this.age = age;
        this.id = id;
        this.name = name;
    }
}

