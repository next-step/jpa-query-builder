package persistence;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class ColumnMap {
    private final LinkedHashMap<String, String> map;

    private ColumnMap(LinkedHashMap<String, String> map) {
        this.map = map;
    }

    public ColumnMap() {
        this(new LinkedHashMap<>());
    }

    public void add(String name, String value) {
        map.put(name, value);
    }

    public String names() {
        return String.join(",", map.keySet());
    }

    public String values() {
        return map.values().stream()
                .map(value -> "'" + value + "'")
                .collect(Collectors.joining(","));
    }
}
