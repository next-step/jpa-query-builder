package persistence.sql.ddl.extractor;

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

class TableExtractorTest {

    @Test
    @DisplayName("getName: @Table(name)이 있다면 name을 반환한다")
    void testGetNameWithAnnotation() {
        TableExtractor tableExtractor = new TableExtractor(WithTableName.class);
        assertThat(tableExtractor.getName()).isEqualTo("with_table_name");
    }

    @Test
    @DisplayName("getName: @Table(name)이 없다면 소문자 클래스 이름 반환한다")
    void testGetName() {
        TableExtractor tableExtractor = new TableExtractor(WithoutTableName.class);
        assertThat(tableExtractor.getName()).isEqualTo("withouttablename");
    }

}
