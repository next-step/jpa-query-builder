package persistence.sql.dml.where;

import persistence.sql.dml.ValueClause;

public class KeyCondition {
    private String key;
    private ValueClause value;
    private ConditionType conditionType;

    public KeyCondition(String key, ValueClause value, ConditionType conditionType) {
        this.key = key;
        this.value = value;
        this.conditionType = conditionType;
    }

    public String getKey() {
        return key;
    }

    public ValueClause getValue() {
        return value;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }
}
