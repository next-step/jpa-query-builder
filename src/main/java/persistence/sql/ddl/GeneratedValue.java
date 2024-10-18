package persistence.sql.ddl;

import jakarta.persistence.GenerationType;
import java.util.HashMap;
import java.util.Map;

public class GeneratedValue {

    private final Map<GenerationType, String> map = new HashMap<>();

    public GeneratedValue() {
        registerGeneratedValueStrategy();
    }

    protected void registerGeneratedValueStrategy() {
        map.put(GenerationType.IDENTITY, "AUTO_INCREMENT");
    }

    public String getStrategy(GenerationType strategy) {
        return map.get(strategy);
    }

}
