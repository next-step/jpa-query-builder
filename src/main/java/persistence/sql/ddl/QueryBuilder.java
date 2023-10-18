package persistence.sql.ddl;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import persistence.dialect.Dialect;
import persistence.meta.EntityMeta;
import persistence.vender.dialect.H2Dialect;

public abstract class QueryBuilder<T> {
    protected static final String DEFAULT_COLUMNS_BRACE = " (%s)";
    protected static final String MARGIN = " ";
    protected static final String EMPTY = "";
    protected final EntityMeta entityMeta;
    protected final Dialect dialect;

    protected QueryBuilder(EntityMeta entityMeta) {
        this(entityMeta, new H2Dialect());
    }

    protected QueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        this.dialect = dialect;
        this.entityMeta = entityMeta;
    }

    protected String brace(String... query) {
        return brace(Arrays.asList(query));
    }

    protected String brace(Collection<String> query) {
        return String.format(DEFAULT_COLUMNS_BRACE, combinedString(query));
    }
    protected String braceWithComma(Collection<String> values) {
        return String.format(DEFAULT_COLUMNS_BRACE, String.join(", ", values));
    }

    protected String combinedString(Collection<String> queries) {
        return queries.stream()
                .filter(it -> !it.isBlank())
                .collect(Collectors.joining(EMPTY));
    }

    protected String combinedQuery(Collection<String> queries) {
        return queries.stream()
                .filter(it -> !it.isBlank())
                .collect(Collectors.joining(MARGIN));
    }

    protected String combinedQuery(String... queries) {
        return combinedQuery(Arrays.asList(queries));
    }
}
