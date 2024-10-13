package persistence.sql.meta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fixture.EntityWithId;
import persistence.fixture.EntityWithoutID;
import persistence.fixture.EntityWithoutTable;
import persistence.fixture.NotEntity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TableTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // when
        final Table table = new Table(EntityWithId.class);

        // then
        assertThat(table).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외를 발생한다.")
    void constructor_exception() {
        // when & then
        assertThatThrownBy(() -> new Table(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Table.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("쿼리를 반환한다.")
    void getQuery() {
        // given
        final Table table = new Table(EntityWithId.class);

        // when
        final String query = table.getQuery("DROP TABLE %s", "users");

        // then
        assertThat(query).isEqualTo("DROP TABLE users");
    }

    @Test
    @DisplayName("@Table 애노테이션이 있는 엔티티로 테이블명을 반환한다.")
    void getTableName_withTable() {
        // given
        final Table table = new Table(EntityWithId.class);

        // when
        final String tableName = table.getTableName();

        // then
        assertThat(tableName).isEqualTo("users");
    }

    @Test
    @DisplayName("@Table 애노테이션이 없는 엔티티로 테이블명을 반환한다.")
    void getTableName_withoutTable() {
        // given
        final Table table = new Table(EntityWithoutTable.class);

        // when
        final String tableName = table.getTableName();

        // then
        assertThat(tableName).isEqualTo("entitywithouttable");
    }

    @Test
    @DisplayName("필드 리스트를 반환한다.")
    void getFields() {
        // given
        final Table table = new Table(EntityWithId.class);

        // when
        final List<Field> fields = table.getFields();

        // then
        assertThat(fields).containsAll(Arrays.stream(EntityWithId.class.getDeclaredFields()).toList());
    }

    @Test
    @DisplayName("where절을 반환한다.")
    void getWhereClause() {
        // given
        final Table table = new Table(EntityWithId.class);

        // when
        final String whereClause = table.getWhereClause(1);

        // then
        assertThat(whereClause).isEqualTo("id = 1");
    }

    @Test
    @DisplayName("@ID 애노테이션이 없는 엔티티로 where절을 반환면 예외를 발생한다.")
    void getWhereClause_exception() {
        // given
        final Table table = new Table(EntityWithoutID.class);

        // when & then
        assertThatThrownBy(() -> table.getWhereClause(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(Table.NOT_ID_FAILED_MESSAGE);
    }
}
