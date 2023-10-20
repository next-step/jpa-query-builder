package persistence.sql.dml;

import java.util.List;

import persistence.sql.common.Table;
import utils.ConditionUtils;

public class SelectQuery extends Table {

    private static final String DEFAULT_SELECT_COLUMN_QUERY = "SELECT %s FROM %s";
    private static final String CONDITION_AND = " AND ";

    private final String methodName;
    private final Object[] args;

    public <T> SelectQuery(Class<T> tClass, String methodName, Object[] args) {
        super(tClass);
        this.methodName = methodName;
        this.args = args;
    }

    public static <T> String create(Class<T> tClass, String methodName) {
        return new SelectQuery(tClass, methodName, null).combine();
    }

    public static <T> String create(Class<T> tClass, String methodName, Object... args) {
        return new SelectQuery(tClass, methodName, args).combine();
    }

    public String combine() {
        String query = parseFiled();

        if (isCondition(methodName)) {
            query = String.join(" ", query, parseWhere());
        }

        return query;
    }

    private String parseFiled() {
        return String.format(DEFAULT_SELECT_COLUMN_QUERY, parseSelectFiled(), getTableName());
    }

    private String parseSelectFiled() {
        return getColumnsWithComma();
    }

    private String parseWhere() {
        //TODO: 메소드명을 읽고, 필드 리스트에서 이를 꺼내오는 방식으로 개선이 필요할 것 같음
        String conditionText = methodName.replace("find", "").replace("By", "");
        List<String> conditionList = ConditionUtils.getWordsFromCamelCase(conditionText);

        String condtion = ConditionBuilder.getCondition(conditionList, args);
        return condtion.replace(" id ", " " + setConditionField("id") + " ");
    }

    private boolean isCondition(String methodName) {
        return methodName.contains("By");
    }

    private String setConditionField(String word) {
        if(word.equals("id")) {
            word = getIdName();
        }
        return word;
    }
}
