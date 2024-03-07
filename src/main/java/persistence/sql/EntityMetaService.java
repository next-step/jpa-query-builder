package persistence.sql;

import persistence.sql.dto.db.Column;
import persistence.sql.dto.db.Table;
import persistence.sql.loader.ClassComponentLoader;
import persistence.sql.loader.ClassFieldLoader;
import persistence.sql.loader.ClassNameLoader;
import persistence.sql.query.QueryBuilder;
import persistence.sql.translator.ClassComponentTranslator;
import persistence.sql.translator.ClassFieldTranslator;
import persistence.sql.translator.ClassNameTranslator;

import java.util.List;
import java.util.Map;

// TODO: table, column 정보 캐싱
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
        return queryBuilder.createTableQuery(table, columns);
    }

    public String generateDropTableQuery(Class<?> clazz) {
        Table table = (Table) invoke(ClassComponentType.CLASS_NAME, clazz);
        return queryBuilder.dropTableQuery(table);
    }

    public String generateInsertQuery(Class<?> clazz, List<Object> values) {
        Table table = (Table) invoke(ClassComponentType.CLASS_NAME, clazz);
        List<Column> columns = (List<Column>) invoke(ClassComponentType.CLASS_FIELD, clazz);
        return queryBuilder.insertQuery(table, columns, values);
    }

    private Object invoke(ClassComponentType type, Class<?> clazz) {
        Object loaded = CLASS_COMPONENT_LOADERS.get(type).invoke(clazz);
        return CLASS_COMPONENT_TRANSLATORS.get(type).invoke(loaded);
    }
}
