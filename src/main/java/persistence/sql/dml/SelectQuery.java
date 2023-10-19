package persistence.sql.dml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import persistence.sql.common.Table;

public class SelectQuery extends Table {

    private static final String DEFAULT_SELECT_COLUMN_QUERY = "SELECT %s FROM %s";
    private static final String DEFAULT_WHERE_QUERY = "WHERE";
    private static final String DEFAULT_EQUALS = "=";
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

    public String parseWhere() {
        String conditionText = methodName.replace("find", "").replace("By", "");
        List<String> conditionList = getWordsFromCamelCase(conditionText);

        String condition = IntStream.range(0, conditionList.size())
            .mapToObj(i -> {
                if (i % 2 == 0) {
                    //칼럼임
                    return String.join(" ", conditionList.get(i), DEFAULT_EQUALS, args[i].toString());
                }

                return CONDITION_AND;
            }).collect(Collectors.joining(" "));

        return String.join(" ", DEFAULT_WHERE_QUERY, condition);
    }

    private boolean isCondition(String methodName) {
        return methodName.contains("By");
    }

    //TODO: StringUtils로 분리할 수 있을 것 같음
    protected List<String> getWordsFromCamelCase(String input) {
        List<String> words = new ArrayList<>();
        int length = input.length();
        int prev = 0;
        for (int i = 1; i < length; i++) {
            //TODO: 메소드 분리?
            if (Character.isUpperCase(input.charAt(i))) {
                String word = input.substring(prev, i);

                words.add(setConditionField(word));
                prev = i;
            }
        }

        words.add(setConditionField(input.substring(prev)));
        return words;
    }

    private String setConditionField(String word) {
        if(word.equals("Id")) {
            word = getIdName();
        }

        return word.toLowerCase();
    }
}
