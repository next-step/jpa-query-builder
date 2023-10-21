package persistence.sql.schema;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.ColumnType;
import persistence.sql.dialect.H2ColumnType;

@DisplayName("EntityObjectMappingMeta 테스트")
class EntityObjectMappingMetaTest {

    private final ColumnType columnType = new H2ColumnType();

    @Test
    @DisplayName("Object에 대해 매핑되는 EntityObjectMappingMeta에서 대응되는 테이블 이름을 확인할 수 있다.")
    void canReadTableNameByObjectMappingMeta() {
        ObjectMappingTestFixture objectMappingTestFixture = new ObjectMappingTestFixture("james", "james@gmail.com");

        final EntityClassMappingMeta classMappingMeta = EntityClassMappingMeta.of(objectMappingTestFixture.getClass(), columnType);
        final EntityObjectMappingMeta objectMappingMeta = EntityObjectMappingMeta.of(objectMappingTestFixture, classMappingMeta);

        assertThat(objectMappingMeta.getTableName()).isEqualTo("OBJECTMAPPINGTESTFIXTURE");
    }

    @Test
    @DisplayName("Object에 대해 매핑되는 EntityObjectMappingMeta에서 대응되는 필드와 값을 확인할 수 있다.")
    void canReadObjectMappingMetaFieldAndValue() {
        ObjectMappingTestFixture objectMappingTestFixture = new ObjectMappingTestFixture("james", "james@gmail.com");

        final EntityClassMappingMeta classMappingMeta = EntityClassMappingMeta.of(objectMappingTestFixture.getClass(), columnType);
        final EntityObjectMappingMeta objectMappingMeta = EntityObjectMappingMeta.of(objectMappingTestFixture, classMappingMeta);

        final Map<String, Object> valueMapByColumnName = objectMappingMeta.getValueMapByColumnName();

        final String idColumName = "id";
        final String nameColumnName = "name";
        final String emailColumnName = "email";

        assertAll(
            () -> assertThat(objectMappingMeta.getColumnMetaSet().size()).isEqualTo(3),
            () -> assertThat(objectMappingMeta.getColumnMetaSet()).extracting("columnName")
                .containsExactly(
                    idColumName, nameColumnName, emailColumnName
                ),
            () -> assertThat(valueMapByColumnName.get(idColumName)).isEqualTo(null),
            () -> assertThat(valueMapByColumnName.get(nameColumnName)).isEqualTo("james"),
            () -> assertThat(valueMapByColumnName.get(emailColumnName)).isEqualTo("james@gmail.com")
        );
    }

    @Entity
    static class ObjectMappingTestFixture {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        private String email;

        public ObjectMappingTestFixture(String name, String email) {
            this.name = name;
            this.email = email;
        }

        protected ObjectMappingTestFixture() {

        }
    }
}