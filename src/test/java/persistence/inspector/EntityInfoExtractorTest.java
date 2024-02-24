package persistence.inspector;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityInfoExtractorTest {

    Class<Person> personClass = Person.class;

    @Test
    void getEntityFieldsForInsert() {

        List<Field> entityFieldsForInsert = EntityInfoExtractor.getEntityFieldsForInsert(personClass);

        assertThat(entityFieldsForInsert).hasSize(3);
    }

    @Test
    void getIdColumnName() {
        String idColumnName = EntityInfoExtractor.getIdColumnName(personClass);

        assertThat(idColumnName).isEqualTo("id");
    }

    @Test
    void getColumnName() throws NoSuchFieldException {
        Field age = personClass.getDeclaredField("age");

        assertThat(EntityInfoExtractor.getColumnName(age)).isEqualTo("old");
    }

    @Test
    void getTableName() {
        String name = personClass.getAnnotation(Table.class).name();

        assertThat(EntityInfoExtractor.getTableName(personClass)).isEqualTo(name);
    }

    @Test
    void getIdField() {
        Field realIdField = Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .get();
        Field findIdField = EntityInfoExtractor.getIdField(personClass);

        assertThat(findIdField).isEqualTo(realIdField);
    }

    @Test
    void isPrimaryKey() {
        Field realIdField = Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .get();

        assertThat(EntityInfoExtractor.isPrimaryKey(realIdField)).isTrue();
    }

}
