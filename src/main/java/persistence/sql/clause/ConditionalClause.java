package persistence.sql.clause;

public interface ConditionalClause extends Clause {
    String operator();

    @Override
    default String clause() {
        return "%s %s %s".formatted(column(), operator(), value());
    }
}
