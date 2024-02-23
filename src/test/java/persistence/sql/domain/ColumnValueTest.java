package persistence.sql.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ColumnValueTest {

    @Test
    void should_append_colon_if_string() throws NoSuchFieldException {
        TestClass testClass = new TestClass();

        ColumnValue strValue = new ColumnValue(TestClass.class.getDeclaredField("name"), testClass);
        ColumnValue longValue = new ColumnValue(TestClass.class.getDeclaredField("id"), testClass);

        assertThat(strValue.getValue()).isEqualTo("'cs'");
        assertThat(longValue.getValue()).isEqualTo("1");
    }

    private class TestClass {
        private final String name = "cs";

        private final Long id = 1l;
    }

}
