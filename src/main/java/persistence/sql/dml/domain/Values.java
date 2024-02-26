package persistence.sql.dml.domain;

import java.util.List;

public class Values {

    private final List<Value> values;

    public Values(List<Value> values) {
        this.values = values;
    }

    public List<Value> getValues() {
        return values;
    }

}
