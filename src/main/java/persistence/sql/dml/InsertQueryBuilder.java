package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.MetaData;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InsertQueryBuilder extends QueryBuilder {

    private Query query;
    public InsertQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public Query insert(Class<?> domain) {
        StringBuilder sb = new StringBuilder();
        // 여기서 도메인 컬럼, value 빼서

        //여기서 인서트문 만들어주기
        StringBuilder query = sb.append("insert into ")
//                .append(metaData.getEntity()).append("(");
        return new Query(query);
    }

    private String columnClause(Class<?> domain) {
        Class<? extends Class> domainClass = domain.getClass();
        Arrays.stream(domain.getDeclaredFields()).forEach(field -> {
            int modifiers = field.getModifiers();
            if(Modifier.isPrivate(modifiers)) {
                field.setAccessible(true);
            }
            try {
                Object o = field.get(domain);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
