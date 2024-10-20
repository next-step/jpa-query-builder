package orm.dsl;

import orm.TableField;
import orm.dsl.condition.Conditions;

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
     * 컬럼과 값의 쌍을 콤마로 구분하여 반환
     * 업데이트 구문에 사용된다.
     */
    public String joinColumnAndValuePairWithComma(List<? extends TableField> columns) {
        return columns.stream()
                .map(column -> "%s=%s".formatted(column.getFieldName(), renderFieldValue(column)))
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

    public String renderWhere(Conditions conditions) {
        if (conditions.hasNoCondition()) {
            return "";
        }

        return " WHERE " + conditions.renderCondition();
    }
}
