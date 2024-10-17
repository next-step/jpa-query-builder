package persistence.sql;

import jakarta.persistence.GenerationType;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.IncorrectGenerationTypeException;
import persistence.sql.exception.NotSupportStrategyTypeException;

import java.util.Map;
import java.util.Optional;

public class H2Dialect implements Dialect {

    private static final Map<GenerationType, String> ddlStrategyDDLString = Map.of(
            GenerationType.IDENTITY, "AUTO_INCREMENT"
    );

    @Override
    public String getGenerationTypeQuery(GenerationType generationType) {
        if (generationType == null) {
            throw new IncorrectGenerationTypeException(ExceptionMessage.INCORRECT_GENERATION_TYPE);
        }

        return Optional.ofNullable(ddlStrategyDDLString.get(generationType))
                .orElseThrow(() -> new NotSupportStrategyTypeException(ExceptionMessage.NOT_SUPPORT_STRATEGY_TYPE));
    }

}
