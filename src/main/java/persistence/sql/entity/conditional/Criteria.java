package persistence.sql.entity.conditional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Criteria {
    private static final String EMPTY = "";
    private static final String DELIMITER = "AND";

    private final List<Criterion> criterion;

    public Criteria(List<Criterion> criterion) {
        this.criterion = criterion;
    }

    public static Criteria emptyInstance() {
        return new Criteria(Collections.emptyList());
    }

    public String toSql() {
        if(criterion.isEmpty()) {
            return EMPTY;
        }

        return criterion.stream()
                .map(Criterion::toSql)
                .collect(Collectors.joining(DELIMITER));
    }
}
