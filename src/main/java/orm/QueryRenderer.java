package orm;

import orm.dsl.condition.Condition;
import orm.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryRenderer {

    /**
     * 컬럼들을 콤마로 구분하여 반환
     */
    public String singleColumnListDotted(List<? extends TableField> columns) {
        return columns.stream()
                .map(TableField::getFieldName)
                .collect(Collectors.joining(","));
    }

    /**
     * 컬럼들을 콤마로 구분하여 반환, 양 끝을 ()로 감싼다.
     */
    public String singleColumnListEnclosed(List<? extends TableField> columns) {
        return "(%s)".formatted(columns.stream()
                .map(TableField::getFieldName)
                .collect(Collectors.joining(",")));
    }

    /**
     * bulk insert 를 위해 (...), (...), (...) 형태로 변환
     */
    public String renderBulkInsertValues(List<List<? extends TableField>> columns) {
        List<String> result = new ArrayList<>(columns.size());
        for (List<? extends TableField> inertValue : columns) {
            result.add("(%s)".formatted(inertValue.stream()
                    .map(TableField::getFieldValue)
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            );
        }

        return String.join(", ", result);
    }

    public String renderWhere(List<Condition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return "";
        }

        final StringBuilder whereBuilder = new StringBuilder();
        whereBuilder.append(" WHERE ");
        whereBuilder.append(
                conditions.stream()
                        .map(Condition::toString)
                        .collect(Collectors.joining(" AND "))
        );

        return whereBuilder.toString();
    }
}
