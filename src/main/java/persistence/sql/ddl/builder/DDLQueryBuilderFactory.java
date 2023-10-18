package persistence.sql.ddl.builder;

import persistence.sql.ddl.converter.JavaToSqlConverter;
import persistence.sql.ddl.converter.JavaToSqlMapper;
import persistence.sql.ddl.model.DDLType;
import persistence.sql.ddl.model.DatabaseType;
import persistence.sql.ddl.validator.DDLQueryValidator;
import persistence.sql.infra.H2JavaToSqlMapper;
import persistence.sql.infra.QueryValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class DDLQueryBuilderFactory {
    private static final Map<DatabaseType, JavaToSqlMapper> JAVA_TO_SQL_MAPPER_MAP = new HashMap<>();
    private static final Map<DDLType, BiFunction<QueryValidator, JavaToSqlConverter, DDLQueryBuilder>> QUERY_BUILDER_MAP = new HashMap<>();

    static {
        JAVA_TO_SQL_MAPPER_MAP.put(DatabaseType.H2, new H2JavaToSqlMapper());

        QUERY_BUILDER_MAP.put(DDLType.CREATE, CreateDDLQueryBuilder::new);
        QUERY_BUILDER_MAP.put(DDLType.DROP, DropDDLQueryBuilder::new);
    }

    public static DDLQueryBuilder createQueryBuilder(DDLType ddlType, DatabaseType databaseType) {
        JavaToSqlMapper mapper = JAVA_TO_SQL_MAPPER_MAP.get(databaseType);

        if (mapper == null) {
            throw new IllegalArgumentException(String.format("[%s] 잘못된 데이터베이스 타입이 들어왔습니다.", databaseType));
        }

        JavaToSqlConverter javaToSqlConverter = new JavaToSqlConverter(mapper);

        BiFunction<QueryValidator, JavaToSqlConverter, DDLQueryBuilder> builderFunction = QUERY_BUILDER_MAP.get(ddlType);

        if (builderFunction == null) {
            throw new IllegalArgumentException(String.format("[%s] 잘못된 DDL type이 들어왔습니다.", ddlType));
        }

        return builderFunction.apply(DDLQueryValidator.getInstance(), javaToSqlConverter);
    }
}
