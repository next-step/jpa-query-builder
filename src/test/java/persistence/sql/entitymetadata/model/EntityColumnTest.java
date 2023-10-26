package persistence.sql.entitymetadata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnTest {
    @Entity
    static class FakeEntityForEntityColumnTest {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long noneColumnAnnotationField;
        @Column
        private Long noneColumnAnnotationValueField;
        @Column(name = "this_is_column_name")
        private Long columnAnnotationField;

        private int primitiveIntField;
        private Long longField;
        private String StringField;
    }

    private Class<FakeEntityForEntityColumnTest> testEntityClass = FakeEntityForEntityColumnTest.class;

    @DisplayName("EntityColumn 생성 (Colmun 어노테이션 생략)")
    @Test
    void create_1() throws NoSuchFieldException {
        Field field = testEntityClass.getDeclaredField("noneColumnAnnotationField");
        EntityColumn<FakeEntityForEntityColumnTest, Long> entityColumn = new EntityColumn<>(testEntityClass, field);

        assertThat(entityColumn.getDbColumnName()).isEqualTo("noneColumnAnnotationField");
    }

    @DisplayName("EntityColumn 생성 (Column 어노테이션의 기본값)")
    @Test
    void create_2() throws NoSuchFieldException {
        Field field = testEntityClass.getDeclaredField("noneColumnAnnotationValueField");
        EntityColumn<FakeEntityForEntityColumnTest, Long> entityColumn = new EntityColumn<>(testEntityClass, field);

        assertThat(entityColumn.getDbColumnName()).isEqualTo("noneColumnAnnotationValueField");
    }

    @DisplayName("EntityColumn 생성 (Column 어노테이션의 name값)")
    @Test
    void create_3() throws NoSuchFieldException {
        Field field = testEntityClass.getDeclaredField("columnAnnotationField");
        EntityColumn<FakeEntityForEntityColumnTest, Long> entityColumn = new EntityColumn<>(testEntityClass, field);

        assertThat(entityColumn.getDbColumnName()).isEqualTo("this_is_column_name");
    }

    @DisplayName("EntityColumn 생성 (type = {0}, name = {1})")
    @ParameterizedTest
    @ArgumentsSource(EntityColumnTestArgumentsProvider.class)
    void create_4(Class<?> typeClass, String fieldName) throws NoSuchFieldException {
        Field field = testEntityClass.getDeclaredField(fieldName);
        EntityColumn<FakeEntityForEntityColumnTest, Long> entityColumn = new EntityColumn<>(testEntityClass, field);

        assertThat(entityColumn.getDbColumnName()).isEqualTo(fieldName);
        assertThat(entityColumn.getType()).isEqualTo(typeClass);
    }

    @DisplayName("EntityColumn 동등성 비교 (entityClass와 fieldType, feldName이 같다면 동등)")
    @Test
    void equals() throws NoSuchFieldException {
        Field field = testEntityClass.getDeclaredField("columnAnnotationField");
        EntityColumn<FakeEntityForEntityColumnTest, Long> entityColumn = new EntityColumn<>(testEntityClass, field);
        EntityColumn<FakeEntityForEntityColumnTest, Long> entityColumn2 = new EntityColumn<>(testEntityClass, field);

        assertThat(entityColumn).isEqualTo(entityColumn2);
        assertThat(entityColumn.equals(entityColumn2)).isTrue();
    }

    @DisplayName("EntityColumn의 dbColumnName과 entityFieldName")
    @Test
    void entityFieldNameAndDbColumnName() throws NoSuchFieldException {
        Field field = testEntityClass.getDeclaredField("columnAnnotationField");
        EntityColumn<FakeEntityForEntityColumnTest, Long> entityColumn = new EntityColumn<>(testEntityClass, field);

        assertThat(entityColumn.getDbColumnName()).isEqualTo("this_is_column_name");
        assertThat(entityColumn.getEntityFieldName()).isEqualTo("columnAnnotationField");
    }


    static class EntityColumnTestArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(int.class, "primitiveIntField"),
                    Arguments.of(Long.class, "longField"),
                    Arguments.of(String.class, "StringField")
            );
        }
    }

}
