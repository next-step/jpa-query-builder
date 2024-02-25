package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dto.db.CreateTableComponent;
import persistence.sql.ddl.dto.db.DBColumn;
import persistence.sql.ddl.dto.db.TableName;
import persistence.sql.ddl.loader.ClassNameLoader;
import persistence.sql.ddl.loader.ClassComponentLoader;
import persistence.sql.ddl.loader.ClassFieldLoader;
import persistence.sql.ddl.query.generator.CreateTableQueryGenerator;
import persistence.sql.ddl.targetentity.Person;
import persistence.sql.ddl.translator.ClassNameTranslator;
import persistence.sql.ddl.translator.ClassComponentTranslator;
import persistence.sql.ddl.translator.ClassFieldTranslator;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTableQueryGeneratorTest {

    private static final Map<ClassComponentType, ClassComponentLoader> LOADERS = Map.of(
            ClassComponentType.CLASS_NAME, new ClassNameLoader(),
            ClassComponentType.CLASS_FIELD, new ClassFieldLoader()
    );

    private static final Map<ClassComponentType, ClassComponentTranslator> TRANSLATORS = Map.of(
            ClassComponentType.CLASS_NAME, new ClassNameTranslator(),
            ClassComponentType.CLASS_FIELD, new ClassFieldTranslator()
    );

    @Test
    void 요구사항_1_Person_클래스_정보를_통해_create_쿼리_생성() {
        // given
        TableName tableName = (TableName) invoke(ClassComponentType.CLASS_NAME).get(0);
        List<DBColumn> dbColumns = (List<DBColumn>) invoke(ClassComponentType.CLASS_FIELD);
        CreateTableComponent createTableComponent = new CreateTableComponent(tableName, dbColumns);

        // when
        String result = CreateTableQueryGenerator.generateSql(createTableComponent);

        // then
        assertThat(result).isEqualTo("create table person (\n" +
                "    id BIGINT PRIMARY KEY,\n" +
                "    name VARCHAR,\n" +
                "    age BIGINT\n" +
                ")");
    }

    private List<?> invoke(ClassComponentType type) {
        List loaded = LOADERS.get(type).invoke(Person.class);
        return TRANSLATORS.get(type).invoke(loaded);
    }
}
