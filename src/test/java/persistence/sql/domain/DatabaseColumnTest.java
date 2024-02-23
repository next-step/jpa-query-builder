package persistence.sql.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseColumnTest {

    @Test
    void should_return_where_clause() throws NoSuchFieldException {
        TestClass testClass = new TestClass();

        DatabaseColumn strColumn = DatabaseColumn.fromField(TestClass.class.getDeclaredField("name"), testClass);
        DatabaseColumn longColumn = DatabaseColumn.fromField(TestClass.class.getDeclaredField("id"), testClass);

        assertThat(strColumn.whereClause()).isEqualTo("name='cs'");
        assertThat(longColumn.whereClause()).isEqualTo("id=1");
    }

    private class TestClass {

        private final Long id = 1l;

        private final String name = "cs";
    }
}
