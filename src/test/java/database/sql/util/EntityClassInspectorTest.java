package database.sql.util;

import database.sql.ddl.OldPerson1;
import database.sql.dml.Person4;
import database.sql.util.column.IColumn;
import database.sql.util.type.MySQLTypeConverter;
import database.sql.util.type.TypeConverter;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class EntityClassInspectorTest {
    EntityClassInspector inspector = new EntityClassInspector(Person4.class);

    @Test
    void getTableName() {
        assertThat(inspector.getTableName()).isEqualTo("users");
    }

    @Test
    void getTableNameWithoutTableAnnotation() {
        EntityClassInspector inspector = new EntityClassInspector(OldPerson1.class);
        assertThat(inspector.getTableName()).isEqualTo("OldPerson1");
    }

    @Test
    void getColumnName() {
        IColumn column = inspector.getPrimaryKeyColumn();
        assertThat(column.getColumnName()).isEqualTo("id");
    }

    @Test
    void toColumnDefinition() {
        TypeConverter typeConverter = new MySQLTypeConverter();
        IColumn column = inspector.getPrimaryKeyColumn();
        assertThat(column.toColumnDefinition(typeConverter)).isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY");
    }

    @Test
    void getVisibleColumns() {
        TypeConverter typeConverter = new MySQLTypeConverter();
        List<String> columnDefinitions = inspector.getColumns()
                .map(column -> column.toColumnDefinition(typeConverter))
                .collect(Collectors.toList());

        assertThat(columnDefinitions).containsExactly(
                "id BIGINT AUTO_INCREMENT PRIMARY KEY",
                "nick_name VARCHAR(255) NULL",
                "old INT NULL",
                "email VARCHAR(255) NOT NULL"
        );
    }

    @Test
    void getColumnsForInserting() {
        TypeConverter typeConverter = new MySQLTypeConverter();
        List<String> columnDefinitions = inspector.getColumnsForInserting()
                .map(column -> column.toColumnDefinition(typeConverter))
                .collect(Collectors.toList());

        assertThat(columnDefinitions).containsExactly(
                "nick_name VARCHAR(255) NULL",
                "old INT NULL",
                "email VARCHAR(255) NOT NULL"
        );
    }
}
