package persistence.sql.dml.where;

import persistence.sql.dml.ValueClause;

import java.util.ArrayList;
import java.util.List;

public class WhereQuery {
    private final List<KeyCondition> keyConditions = new ArrayList<>();

    public void addKey(String key, ValueClause value, ConditionType conditionType) {
        this.keyConditions.add(new KeyCondition(key, value, conditionType));
    }

    public List<KeyCondition> getKeyConditions() {
        return keyConditions;
    }
}
