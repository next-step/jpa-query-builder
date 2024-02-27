package persistence.sql.mapping;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;



@Entity
@Table(name = "with_table_name")
class WithTableName {
    @Id
    private int id;
}

@Entity
class WithoutTableName {
    @Id
    private int id;
}

class TableDataTest {

    @Test
    @DisplayName("getName: @Table(name)이 있다면 name을 반환한다")
    void testGetNameWithAnnotation() {
        TableData tableData = TableData.from(WithTableName.class);
        assertThat(tableData.getName()).isEqualTo("with_table_name");
    }

    @Test
    @DisplayName("getName: @Table(name)이 없다면 소문자 클래스 이름 반환한다")
    void testGetName() {
        TableData tableData = TableData.from(WithoutTableName.class);
        assertThat(tableData.getName()).isEqualTo("withouttablename");
    }

}
