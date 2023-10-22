package persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnsTest {

    @Entity
    private static class ColumnTestClass {
        @Id
        private Long id;

        @Column
        private String column1;

        private String column2;

        @Transient
        private String transientField;
    }

    @DisplayName("주어진 엔티티에서 Id 클래스를 찾을 수 있다.")
    @Test
    void findIdField() throws Exception {
        assertThat(new EntityColumns(ColumnTestClass.class).getIdColumn())
                .isEqualTo(new EntityColumn(ColumnTestClass.class.getDeclaredField("id")));
    }

    @DisplayName("@Transient 클래스는 EntityData에 포함하지 않는다.")
    @Test
    void exceptTransientField() throws Exception {
        assertThat(new EntityColumns(ColumnTestClass.class).getEntityColumnList())
                .doesNotContain(new EntityColumn(ColumnTestClass.class.getDeclaredField("transientField")));
    }

}
