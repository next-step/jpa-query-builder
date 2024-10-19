package orm.dsl;

import orm.TableField;
import orm.dsl.condition.Condition;
import orm.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryRenderer {

    /**
     * 컬럼들을 콤마로 구분하여 반환
     */
    public String joinColumnNamesWithComma(List<? extends TableField> columns) {
        return columns.stream()
                .map(TableField::getFieldName)
                .collect(Collectors.joining(","));
    }

    /**
     * 대량 삽입 SQL 문에 대한 값의 문자열 표현을 렌더링 합니다.
     *
     * <p>예를 들어, 이러한 형태가 됩니다 : (id,name,age) </p>
     **/
    public String renderBulkInsertValues(List<List<? extends TableField>> columns) {
        List<String> result = new ArrayList<>(columns.size());
        for (List<? extends TableField> inertValue : columns) {
            result.add("(%s)".formatted(inertValue.stream()
                    .map(this::renderFieldValue)
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            );
        }

        return String.join(", ", result);
    }

    private Object renderFieldValue(TableField tableField) {
        final Object fieldValue = tableField.getFieldValue();
        if (fieldValue instanceof String) {
            return "'%s'".formatted(fieldValue);
        }
        return fieldValue;
    }

    public String renderWhere(List<Condition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return "";
        }

        return " WHERE " +
                conditions.stream()
                        .map(Condition::toString)
                        .collect(Collectors.joining(" AND "));
    }
}
