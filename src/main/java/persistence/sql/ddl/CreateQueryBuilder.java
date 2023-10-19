package persistence.sql.ddl;

import persistence.sql.QueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder implements QueryBuilder {

    @Override
    public String build(Object obj) {
        Class<?> clazz = obj.getClass();
        EntityQueryBuilder entityQueryBuilder = new EntityQueryBuilder(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append(entityQueryBuilder.getCreateQuery());

        List<FieldQueryBuilder> efs = Arrays.stream(clazz.getDeclaredFields())
                .map(FieldQueryBuilder::new)
                .collect(Collectors.toList());

        efs.stream().forEach(ef -> sb.append(ef.getCreateFieldQuery()));

        sb.append("PRIMARY KEY(");
        efs.stream().filter(ef -> ef.isPk()).forEach(ef -> sb.append(ef.getName()));
        sb.append(")\n");
        sb.append(");\n");

        return sb.toString();
    }
}
