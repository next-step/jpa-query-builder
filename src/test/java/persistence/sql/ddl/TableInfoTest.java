package persistence.sql.ddl;

import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class TableInfoTest {

    @Test
    @DisplayName("@Table 어노테이션 있는 경우")
    public void tableAnnotation() {
        TableInfo tableInfo = new TableInfo(Person.class);

        assertThat(tableInfo.getTableName()).isEqualTo("users");
    }

    @Test
    @DisplayName("@Table 어노테이션 있는데 name이 빈 값인 경우")
    public void tableAnnotationNameEmptyString() {
        TableInfo tableInfo = new TableInfo(TableAnnotationNameEmpty.class);

        assertThat(tableInfo.getTableName()).isEqualTo("TableAnnotationNameEmpty");
    }

    @Table(name = "")
    private class TableAnnotationNameEmpty {
    }

}
