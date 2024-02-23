package database.sql.util;

import database.sql.ddl.OldPerson1;
import database.sql.dml.Person4;
import database.sql.util.type.MySQLTypeConverter;
import database.sql.util.type.TypeConverter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityClassInspectorTest {
    private final TypeConverter typeConverter = new MySQLTypeConverter();
    private final EntityClassInspector inspector = new EntityClassInspector(Person4.class);

    @Test
    void getTableName() {
        String tableName = inspector.getTableName();
        assertThat(tableName).isEqualTo("users");
    }

    @Test
    void getTableNameWithoutTableAnnotation() {
        EntityClassInspector inspector = new EntityClassInspector(OldPerson1.class);
        String tableName = inspector.getTableName();
        assertThat(tableName).isEqualTo("OldPerson1");
    }

    @Test
    void getColumnNames() {
        List<String> columnNames = inspector.getColumnNames();
        assertThat(columnNames).containsExactly("id", "nick_name", "old", "email");
    }

    @Test
    void getColumnDefinitions() {
        List<String> res = inspector.getColumnDefinitions(typeConverter);
        assertThat(res).containsExactly(
                "id BIGINT AUTO_INCREMENT PRIMARY KEY",
                "nick_name VARCHAR(255) NULL",
                "old INT NULL",
                "email VARCHAR(255) NOT NULL");
    }

    @Test
    void getPrimaryKeyField() {
        Field field = inspector.getPrimaryKeyField();
        assertAll(
                () -> assertThat(field.getName()).isEqualTo("id"),
                () -> assertThat(field.getType()).isEqualTo(Long.class)
        );
    }

    @Test
    void getPrimaryKeyColumnName() {
        String columnName = inspector.getPrimaryKeyColumnName();
        assertThat(columnName).isEqualTo("id");
    }

    @Test
    void getColumnNamesForInserting() {
        List<String> columnsForInserting = inspector.getColumnNamesForInserting();
        assertThat(columnsForInserting).containsExactly("nick_name", "old", "email");
    }
}