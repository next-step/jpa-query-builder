package persistence.sql.ddl;

import persistence.sql.ddl.dto.db.Column;
import persistence.sql.ddl.dto.db.Table;
import persistence.sql.ddl.loader.ClassComponentLoader;
import persistence.sql.ddl.loader.ClassFieldLoader;
import persistence.sql.ddl.loader.ClassNameLoader;
import persistence.sql.ddl.query.QueryBuilder;
import persistence.sql.ddl.translator.ClassComponentTranslator;
import persistence.sql.ddl.translator.ClassFieldTranslator;
import persistence.sql.ddl.translator.ClassNameTranslator;

import java.util.List;
import java.util.Map;

public class EntityMetaService {

    private static final Map<ClassComponentType, ClassComponentLoader> CLASS_COMPONENT_LOADERS = Map.of(
            ClassComponentType.CLASS_NAME, new ClassNameLoader(),
            ClassComponentType.CLASS_FIELD, new ClassFieldLoader()
    );

    private static final Map<ClassComponentType, ClassComponentTranslator> CLASS_COMPONENT_TRANSLATORS = Map.of(
            ClassComponentType.CLASS_NAME, new ClassNameTranslator(),
            ClassComponentType.CLASS_FIELD, new ClassFieldTranslator()
    );

    private final QueryBuilder queryBuilder;

    public EntityMetaService(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public String generateCreateTableQuery(Class<?> clazz) {
        Table table = (Table) invoke(ClassComponentType.CLASS_NAME, clazz);
        List<Column> columns = (List<Column>) invoke(ClassComponentType.CLASS_FIELD, clazz);
        return queryBuilder.generateCreateTableSql(table, columns);
    }

    public String generateDropTableQuery(Class<?> clazz) {
        Table table = (Table) invoke(ClassComponentType.CLASS_NAME, clazz);
        return queryBuilder.generateDropTableSql(table);
    }

    private Object invoke(ClassComponentType type, Class<?> clazz) {
        Object loaded = CLASS_COMPONENT_LOADERS.get(type).invoke(clazz);
        return CLASS_COMPONENT_TRANSLATORS.get(type).invoke(loaded);
    }
}
