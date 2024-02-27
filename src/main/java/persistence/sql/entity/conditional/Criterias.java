package persistence.sql.entity.conditional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Criterias {
    private static final String EMPTY = "";
    private static final String DELIMITER = "AND";

    private final List<Criteria> criterias;

    public Criterias(List<Criteria> criterias) {
        this.criterias = criterias;
    }

    public static Criterias emptyInstance() {
        return new Criterias(Collections.emptyList());
    }

    public String toSql() {
        if(criterias.isEmpty()) {
            return EMPTY;
        }

        return criterias.stream()
                .map(Criteria::toSql)
                .collect(Collectors.joining(DELIMITER));
    }
}
