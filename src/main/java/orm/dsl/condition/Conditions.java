package orm.dsl.condition;

import orm.util.CollectionUtils;
import orm.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Conditions {
    private final List<Condition> conditionList;

    public Conditions() {
        this.conditionList = new ArrayList<>();
    }

    public Conditions(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public boolean hasCondition() {
        return conditionList.stream()
                .anyMatch(condition -> StringUtils.isNotBlank(condition.toString()));
    }

    public boolean hasNoCondition() {
        return !hasCondition();
    }

    public void add(Condition condition) {
        conditionList.add(condition);
    }

    public void addAll(List<Condition> conditions) {
        conditionList.addAll(conditions);
    }

    public String renderCondition() {
        return this.conditionList.stream()
                .map(Condition::toString)
                .collect(Collectors.joining(" AND "));
    }

    public void clear() {
        conditionList.clear();
    }
}
