package persistence.sql.dml;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConditionBuilder {
    private static final String DEFAULT_WHERE_QUERY = "WHERE";

    private static final String CONDITION_AND = "AND";
    private static final String DEFAULT_EQUALS = "=";


    public static String getCondition(List<String> conditionList, Object[] args) {
        return IntStream.range(0, conditionList.size())
                .mapToObj(i -> {
                    if (i % 2 == 0) {
                        return getCondition(conditionList.get(i), args[i]);
                    }

                    return CONDITION_AND;
                }).collect(Collectors.joining(" "));

    }

    public static String getCondition(String fieldName, Object args) {
        return String.join(" ", DEFAULT_WHERE_QUERY, fieldName, DEFAULT_EQUALS, args.toString());
    }
}
