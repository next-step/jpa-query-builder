package persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMapTest {

    @Test
    void names() {
        ColumnMap map = new ColumnMap();

        map.add("name", "slow");
        map.add("age", "1");

        assertThat(map.names()).isEqualTo("name,age");
    }

    @Test
    void values() {
        ColumnMap map = new ColumnMap();

        map.add("name", "slow");
        map.add("age", "1");

        assertThat(map.values()).isEqualTo("'slow','1'");
    }
}
