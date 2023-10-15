package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    final private QueryBuilder builder;

    public QueryBuilderTest() {
        Dialect dialect = new H2Dialect();
        this.builder = new QueryBuilder(dialect);
    }

    @Nested
    @DisplayName(".generateCreateDDL")
    class GenerateCreateDDL {

        @Nested
        @DisplayName("@Entity annotation 을 가지고 있지 않다면")
        class HasNoEntityAnnotation {

            class NoEntityClass {}

            @Test
            @DisplayName("에러가 발생한다.")
            void it_raise_an_exception() {
                Assertions.assertThrows(RuntimeException.class, () -> builder.generateTableCreateDDL(NoEntityClass.class));
            }
        }

        @Entity
        class NameOfTable {

            @Id
            private Long primaryKey;
        }

        @Test
        @DisplayName("class의 이름은 table의 이름이다.")
        void class_name_is_name_of_table() {
            final String query = builder.generateTableCreateDDL(NameOfTable.class);
            assertThat(query).contains("CREATE TABLE " + "nameoftable");
        }

        @Nested
        @DisplayName("@Table annotation 을 가지고 있다면")
        class TableAnnotation {
            @Entity
            @Table(name = "newNameOfTable")
            class OriginalNameOfTable {

                @Id
                private Long primaryKey;
            }

            @Test
            @DisplayName("name은 table의 이름이 된다.")
            void name_is_name_of_table() {
                final String query = builder.generateTableCreateDDL(OriginalNameOfTable.class);
                assertThat(query).contains("CREATE TABLE " + "newnameoftable");
            }
        }
    }

    @Nested
    @DisplayName(".getCreateColumnQueries")
    class GetCreateColumnQueries {

        @Nested
        @DisplayName("Transient Annotation 의 경우")
        class TransientAnnotation {

            @Entity
            class NameOfTable {
                @Id
                private Long primaryKey;

                @Transient
                private String transientField;
            }

            @Test
            @DisplayName("query 에 포함되지 않는다.")
            void it_does_not_contain() throws NoSuchFieldException {
                final List<String> columnQueries = builder.getCreateColumnQueries(
                    new Field[] { NameOfTable.class.getDeclaredField("transientField") }
                );
                assertThat(columnQueries).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName(".fieldToColumnQuery")
    class FieldToColumnQuery {
        @Nested
        @DisplayName("@Id annotation 은")
        class IdAnnotation {

            @Entity
            class NameOfTable {
                @Id
                private Long primaryKey;
            }

            @Test
            @DisplayName("table의 primary key 가 된다.")
            void it_is_primary_key_of_table() throws NoSuchFieldException {
                final String columnQueries = builder.fieldToColumnQuery(NameOfTable.class.getDeclaredField("primaryKey"));
                assertThat(columnQueries).contains("`primaryKey`" + " BIGINT PRIMARY KEY");
            }

            @Nested
            @DisplayName("@GeneratedValue annotation 이 추가된다면")
            class GeneratedValueAnnotation {

                @Entity
                class NameOfTable {
                    @Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    private Long primaryKey;
                }

                @Test
                @DisplayName("AUTO_INCREMENT 옵션이 추가된다.")
                void it_adds_an_auto_increment_option() throws NoSuchFieldException {
                    final String columnQueries = builder.fieldToColumnQuery(NameOfTable.class.getDeclaredField("primaryKey"));
                    assertThat(columnQueries).contains("AUTO_INCREMENT");
                }
            }
        }

        @Nested
        @DisplayName("Annotation 이 없는 필드의 경우")
        class NoAnnotationFields {
            @Entity
            class NameOfTable {
                @Id
                private Long primaryKey;

                private String stringColumn;

                private Integer integerColumn;
            }

            @Test
            @DisplayName("String field 는 table 의 varchar column 이 된다.")
            void string_field_change_to_varchar_column() throws NoSuchFieldException {
                final String columnQueries = builder.fieldToColumnQuery(NameOfTable.class.getDeclaredField("stringColumn"));
                assertThat(columnQueries).contains("`stringColumn`" + " VARCHAR(255)");
            }

            @Test
            @DisplayName("Integer field 는 table 의 integer column 이 된다.")
            void integer_field_change_to_integer_column() throws NoSuchFieldException {
                final String columnQueries = builder.fieldToColumnQuery(NameOfTable.class.getDeclaredField("integerColumn"));
                assertThat(columnQueries).contains("`integerColumn`" + " INTEGER");
            }
        }

        @Nested
        @DisplayName("Column Annotation 의 경우")
        class ColumnAnnotation {
            @Entity
            class NameOfTable {
                @Id
                private Long primaryKey;

                @Column(name = "changed_name")
                private String originalName;

                @Column(nullable = false)
                private String notNullableColumn;
            }

            @Test
            @DisplayName("지정된 name 으로 컬럼 이름이 바뀐다.")
            void name_is_column_name() throws NoSuchFieldException {
                final String columnQueries = builder.fieldToColumnQuery(NameOfTable.class.getDeclaredField("originalName"));
                assertThat(columnQueries).contains("`changed_name`" + " VARCHAR(255)");
            }

            @Test
            @DisplayName("not null 옵션을 제어할 수 있다.")
            void it_handles_nullable_option() throws NoSuchFieldException {
                final String columnQueries = builder.fieldToColumnQuery(NameOfTable.class.getDeclaredField("notNullableColumn"));
                assertThat(columnQueries).contains("`notNullableColumn`" + " VARCHAR(255) NOT NULL");
            }
        }
    }

    @Nested
    @DisplayName(".generateDropDDL")
    class GenerateDropDDL {

        @Nested
        @DisplayName("@Entity annotation 을 가지고 있지 않다면")
        class HasNoEntityAnnotation {

            class NoEntityClass {}

            @Test
            @DisplayName("에러가 발생한다.")
            void it_raise_an_exception() {
                Assertions.assertThrows(RuntimeException.class, () -> builder.generateTableDropDDL(NoEntityClass.class));
            }
        }

        @Entity
        class NameOfTable {

            @Id
            private Long primaryKey;
        }

        @Test
        @DisplayName("class의 이름은 table의 이름이다.")
        void class_name_is_name_of_table() {
            final String query = builder.generateTableDropDDL(NameOfTable.class);
            assertThat(query).contains("DROP TABLE " + "nameoftable");
        }

        @Nested
        @DisplayName("@Table annotation 을 가지고 있다면")
        class TableAnnotation {
            @Entity
            @Table(name = "newNameOfTable")
            class OriginalNameOfTable {

                @Id
                private Long primaryKey;
            }

            @Test
            @DisplayName("name은 table의 이름이 된다.")
            void name_is_name_of_table() {
                final String query = builder.generateTableDropDDL(OriginalNameOfTable.class);
                assertThat(query).contains("DROP TABLE " + "newnameoftable");
            }
        }
    }
}
