package persistence.sql;

import jakarta.persistence.GenerationType;

import java.util.Map;
import java.util.Optional;

public class H2Dialect implements Dialect {

    private static final Map<GenerationType, String> ddlStrategyDDLString = Map.of(
            GenerationType.IDENTITY, "AUTO_INCREMENT"
    );

    @Override
    public String getGenerationTypeQuery(GenerationType generationType) {
        if (generationType == null) {
            throw new IllegalArgumentException("Generation Type이 존재하지 않습니다.");
        }

        return Optional.ofNullable(ddlStrategyDDLString.get(generationType))
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 Strategy 타입이 존재하지 않습니다."));
    }
}
