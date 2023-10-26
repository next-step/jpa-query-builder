package persistence.sql.entitymetadata.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnsTest {

    @DisplayName("EntityColumns 생성")
    @Test
    void create() {
        EntityColumns<Person> entityColumns = new EntityColumns<>(Person.class);

        assertThat(entityColumns.getColumns().size()).isEqualTo(4);
        assertThat(entityColumns.getIdColumn().getDbColumnName()).isEqualTo("id");
        assertThat(entityColumns.getIdColumn().isIdColumn()).isTrue();
        assertThat(entityColumns.getColumns().stream()
                .map(EntityColumn::getDbColumnName)
                .collect(Collectors.toList())).containsExactlyInAnyOrder("id", "nick_name", "old", "email");
    }
}
