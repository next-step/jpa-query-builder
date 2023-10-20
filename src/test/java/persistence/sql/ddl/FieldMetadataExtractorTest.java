package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldMetadataExtractorTest {

    @Test
    @DisplayName("map() 메소드 익셉션 테스트")
    public void mapExceptionTest() throws NoSuchFieldException {
        Field name = Person.class.getDeclaredField("name");

        FieldMetadataExtractor fieldMetaDataExtractor = new FieldMetadataExtractor(name);

        assertThrows(IllegalArgumentException.class, () -> {
            fieldMetaDataExtractor.map(Person.class);
        });
    }

    @Test
    @DisplayName("getDefinition() 메소드 테스트")
    public void getDefinitionTest() throws NoSuchFieldException {
        Field id = Person.class.getDeclaredField("id");

        FieldMetadataExtractor fieldMetaDataExtractor = new FieldMetadataExtractor(id);

        assertThat(fieldMetaDataExtractor.getDefinition()).isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY");
    }

    @Test
    @DisplayName("getDefinition() 메소드 옵션 없는 케이스 테스트")
    public void getDefinitionTestWithNoOptions() throws NoSuchFieldException {
        Field name = Person.class.getDeclaredField("name");

        FieldMetadataExtractor fieldMetaDataExtractor = new FieldMetadataExtractor(name);

        assertThat(fieldMetaDataExtractor.getDefinition()).isEqualTo("nick_name VARCHAR(255)");
    }

    @Test
    @DisplayName("ColumnMetaInfo 메소드 sorting 테스트")
    public void getColumnMetaInfosValueTest() throws NoSuchFieldException {
        Field name = ColumnMetaInfosValueTest.class.getDeclaredField("id");

        FieldMetadataExtractor fieldMetaDataExtractor = new FieldMetadataExtractor(name);

        assertThat(fieldMetaDataExtractor.getDefinition()).isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL");
    }


    private class ColumnMetaInfosValueTest {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        private Long id;

    }
}
