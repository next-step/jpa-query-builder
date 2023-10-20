package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityTable;

import static org.assertj.core.api.Assertions.assertThat;

class CreateDDLQueryBuilderTest {

    @DisplayName("DDL Query 생성")
    @Test
    void build() {
        EntityTable<Person> entityTable = new EntityTable<>(Person.class);
        CreateDDLQueryBuilder<Person> createDDLQueryBuilder = new CreateDDLQueryBuilder<>(DbmsStrategy.H2);

        assertThat(createDDLQueryBuilder.build(entityTable))
                .isEqualTo("CREATE TABLE USERS (\n" +
                        "    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    NICK_NAME VARCHAR(255) NULL,\n" +
                        "    OLD INT NULL,\n" +
                        "    EMAIL VARCHAR(255) NOT NULL\n" +
                        ");");
    }
}