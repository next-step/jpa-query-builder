package persistence.sql.meta;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Column class 의")
class ColumnTest {


    @DisplayName("from 메서드는")
    @Nested
    class From {
        @DisplayName("Entity 클래스의 필드로 Column 객체를 생성한다.")
        @Test
        void testFrom() throws NoSuchFieldException {
            class Entity {
                private int id;
            }

            Column column = Column.from(Entity.class.getDeclaredField("id"));
            assertNotNull(column);
        }
    }

    @DisplayName("getColumnName 메서드는")
    @Nested
    class GetColumnName {

        @DisplayName("Entity 클래스의 필드명을 반환한다.")
        @Test
        void testGetColumnName() throws NoSuchFieldException {

            class Entity {
                private int id;
            }
            Column column = Column.from(Entity.class.getDeclaredField("id"));

            String columnName = column.getColumnName();

            assertEquals("id", columnName);
        }

        @DisplayName("Column 어노테이션이 있을 경우, name 속성값을 반환한다.")
        @Test
        void testGetColumnName_WhenColumnAnnotation() throws NoSuchFieldException {
            class Entity {
                @jakarta.persistence.Column(name = "pk")
                private int id;
            }
            Column column = Column.from(Entity.class.getDeclaredField("id"));

            String columnName = column.getColumnName();

            assertEquals("pk", columnName);
        }

        @DisplayName("Column 어노테이션이 없을 경우, 필드명을 snake_case로 변환하여 반환한다.")
        @Test
        void testGetColumnName_WhenNoColumnAnnotation() throws NoSuchFieldException {
            class Entity {
                private int userId;
            }
            Column column = Column.from(Entity.class.getDeclaredField("userId"));

            String columnName = column.getColumnName();

            assertEquals("user_id", columnName);
        }

    }

    @DisplayName("isNullable 메서드는")
    @Nested
    class IsNullable {
        @DisplayName("Column 어노테이션이 있을 경우, nullable 속성값을 반환한다.")
        @Test
        void testIsNullable_WhenColumnAnnotation() throws NoSuchFieldException {
            class Entity {
                @jakarta.persistence.Column(nullable = false)
                private String name;
            }
            Column column = Column.from(Entity.class.getDeclaredField("name"));

            boolean nullable = column.isNullable();

            assertFalse(nullable);
        }

        @DisplayName("Column 어노테이션이 없을 경우, true를 반환한다.")
        @Test
        void testIsNullable_WhenNoColumnAnnotation() throws NoSuchFieldException {
            class Entity {
                private String name;
            }
            Column column = Column.from(Entity.class.getDeclaredField("name"));

            boolean nullable = column.isNullable();

            assertTrue(nullable);
        }
    }

    @DisplayName("isIdAnnotation 메서드는")
    @Nested
    class IsIdAnnotation {
        @DisplayName("Id 어노테이션이 있을 경우, true를 반환한다.")
        @Test
        void testIsIdAnnotation_WhenIdAnnotation() throws NoSuchFieldException {
            class Entity {
                @Id
                private int id;
            }
            Column column = Column.from(Entity.class.getDeclaredField("id"));

            boolean id = column.isIdAnnotation();

            assertTrue(id);

        }
    }

    @DisplayName("isGeneratedValueAnnotation 메서드는")
    @Nested
    class IsGeneratedValueAnnotation {
        @DisplayName("GeneratedValue 어노테이션이 있을 경우, true를 반환한다.")
        @Test
        void testIsGeneratedValueAnnotation_WhenGeneratedValueAnnotation() throws NoSuchFieldException {
            class Entity {
                @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
                private int id;
            }
            Column column = Column.from(Entity.class.getDeclaredField("id"));

            boolean generatedValue = column.isGeneratedValueAnnotation();

            assertTrue(generatedValue);
        }
    }
}
