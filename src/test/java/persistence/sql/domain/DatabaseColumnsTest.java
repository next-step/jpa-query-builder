package persistence.sql.domain;

import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseColumnsTest {

    private static DatabaseColumns columns;

    @BeforeAll
    static void setUp() throws NoSuchFieldException {
        Class<TestClass> clazz = TestClass.class;
        TestClass instance = new TestClass();
        List<DatabaseColumn> databaseColumns = List.of(
                DatabasePrimaryColumn.fromField(clazz.getDeclaredField("id"), instance),
                DatabaseColumn.fromField(clazz.getDeclaredField("name"), instance),
                DatabaseColumn.fromField(clazz.getDeclaredField("age"), instance)
        );
        columns = new DatabaseColumns(databaseColumns);
    }

    @Test
    void should_return_id_column_name() {
        assertThat(columns.getIdColumnName()).isEqualTo("id");
    }

    @Test
    void should_return_where_clause() {
        assertThat(columns.whereClause()).isEqualTo("id=1 and name='cs' and age=29");
    }

    @Test
    void should_return_column_clause() {
        assertThat(columns.columnClause()).isEqualTo("id,name,age");
    }

    @Test
    void should_return_value_clause() {
        assertThat(columns.valueClause()).isEqualTo("1,'cs',29");
    }

    private static class TestClass {

        @Id
        private final Long id = 1l;

        private final String name = "cs";

        private final Integer age = 29;
    }

}
