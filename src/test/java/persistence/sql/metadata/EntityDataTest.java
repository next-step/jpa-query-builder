package persistence.sql.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EntityDataTest {

    @DisplayName("테이블 이름을 반환한다")
    @Test
    void getTableName() {
        Person entity = createPerson();
        EntityData entityData = EntityData.from(entity);

        String tableName = entityData.getTableName();

        assertThat(tableName).isEqualTo("my_users");
    }

    private Person createPerson() {
        return new Person(1L, "name", 10, "test@email.com", 1);
    }

    @DisplayName("기본 키 컬럼을 반환한다")
    @Test
    void getPrimaryKey() {
        Person person = createPerson();
        EntityData entityData = EntityData.from(person);
        ColumnData primaryKey = entityData.getPrimaryKey();

        String primaryKeyName = primaryKey.getName();

        assertThat(primaryKeyName).isEqualTo("id");
    }

    @DisplayName("insert 컬럼 목록을 반환한다")
    @Test
    void getInsertColumns() {
        Person person = createPerson();
        EntityData entityData = EntityData.from(person);
        ColumnDatas insertColumns = entityData.getInsertColumns();

        List<String> insertColumnNames = insertColumns.getColumnNames();

        assertThat(insertColumnNames).containsExactly("nick_name", "old", "email");
    }
}
