package persistence.sql.dialect;

import persistence.sql.dialect.h2.H2Dialect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DialectFactory {

    private final Map<String, Dialect> dialectMap = new HashMap<>();

    private static final DialectFactory INSTANCE = new DialectFactory();

    private DialectFactory() {
        dialectMap.put("H2", H2Dialect.getInstance());
    }

    public static DialectFactory getInstance() {
        return INSTANCE;
    }

    public Dialect getDialect(String dbmsName) {
        Dialect dialect = dialectMap.get(dbmsName);
        return Optional.ofNullable(dialect).orElseThrow(() -> new IllegalArgumentException("지원하지 않는 데이터베이스입니다."));
    }

}
