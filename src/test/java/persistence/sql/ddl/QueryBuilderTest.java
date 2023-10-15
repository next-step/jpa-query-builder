package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    @Nested
    @DisplayName(".createTable")
    class CreateTable {

        @Nested
        @DisplayName("@Entity annotation 을 가지고 있지 않다면")
        class HasNoEntityAnnotation {

            class NoEntityClass {}

            @Test
            @DisplayName("에러가 발생한다.")
            void it_raise_an_exception() {
                Assertions.assertThrows(RuntimeException.class, () -> {
                    QueryBuilder.createTableSQL(NoEntityClass.class);
                });
            }
        }

        @Entity
        class NameOfTable {

            @Id
            private Long primaryKey;

            private String stringColumn;

            private Integer integerColumn;
        }

        @Test
        @DisplayName("class의 이름은 table의 이름이다.")
        void class_name_is_name_of_table() {
            final String query = QueryBuilder.createTableSQL(NameOfTable.class);;
            assertThat(query).contains("create table " + "nameoftable");
        }
    }

    @Nested
    @DisplayName(".getCreateColumnQueries")
    class CreateColumnQueries {

        @Entity
        class NameOfTable {

            @Id
            private Long primaryKey;

            private String stringColumn;

            private Integer integerColumn;
        }

        ArrayList<String> subject() {
            return QueryBuilder.getCreateColumnQueries(NameOfTable.class.getSimpleName(), NameOfTable.class.getDeclaredFields());
        }

        @Test
        @DisplayName("@Id annotation 은 table의 primary key 가 된다.")
        void id_annotation_is_primary_key_of_table() {
            final ArrayList<String> columnQueries = subject();
            assertThat(columnQueries).contains("primaryKey" + " bigint not null constraint " + NameOfTable.class.getSimpleName() + "_pkey primary key");
        }

        @Test
        @DisplayName("String 은 table 의 varchar column 이 된다.")
        void string_filed_is_varchar_column_of_table() {
            final ArrayList<String> columnQueries = subject();
            assertThat(columnQueries).contains("stringColumn"+ " varchar(255)");
        }

        @Test
        @DisplayName("Integer 는 table 의 integer column 이 된다.")
        void integer_filed_is_integer_column_of_table() {
            final ArrayList<String> columnQueries = subject();
            assertThat(columnQueries).contains("integerColumn"+ " integer");
        }
    }
}
