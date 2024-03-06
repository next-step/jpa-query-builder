package persistence.sql.ddl.query.builder;

import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.db.DBColumn;
import persistence.sql.ddl.dto.db.TableName;
import persistence.sql.ddl.loader.ClassComponentLoader;
import persistence.sql.ddl.loader.ClassFieldLoader;
import persistence.sql.ddl.loader.ClassNameLoader;
import persistence.sql.ddl.query.generator.QueryGenerator;
import persistence.sql.ddl.translator.ClassComponentTranslator;
import persistence.sql.ddl.translator.ClassFieldTranslator;
import persistence.sql.ddl.translator.ClassNameTranslator;

import java.util.List;
import java.util.Map;

public class QueryBuilder {

    private static final Map<ClassComponentType, ClassComponentLoader> CLASS_COMPONENT_LOADERS = Map.of(
            ClassComponentType.CLASS_NAME, new ClassNameLoader(),
            ClassComponentType.CLASS_FIELD, new ClassFieldLoader()
    );

    private static final Map<ClassComponentType, ClassComponentTranslator> CLASS_COMPONENT_TRANSLATORS = Map.of(
            ClassComponentType.CLASS_NAME, new ClassNameTranslator(),
            ClassComponentType.CLASS_FIELD, new ClassFieldTranslator()
    );

    private final QueryGenerator queryGenerator;

    public QueryBuilder(QueryGenerator queryGenerator) {
        this.queryGenerator = queryGenerator;
    }

    public String generateCreateTableQuery(Class<?> clazz) {
        TableName tableName = (TableName) invoke(ClassComponentType.CLASS_NAME, clazz);
        List<DBColumn> dbColumns = (List<DBColumn>) invoke(ClassComponentType.CLASS_FIELD, clazz);
        return queryGenerator.generateCreateTableSql(tableName, dbColumns);
    }

    public String generateDropTableQuery(Class<?> clazz) {
        TableName tableName = (TableName) invoke(ClassComponentType.CLASS_NAME, clazz);
        return queryGenerator.generateDropTableSql(tableName);
    }

    private Object invoke(ClassComponentType type, Class<?> clazz) {
        Object loaded = CLASS_COMPONENT_LOADERS.get(type).invoke(clazz);
        return CLASS_COMPONENT_TRANSLATORS.get(type).invoke(loaded);
    }
}
