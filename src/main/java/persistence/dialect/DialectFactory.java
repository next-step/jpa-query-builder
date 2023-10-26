package persistence.dialect;

import persistence.dialect.H2.H2Dialect;
import persistence.dialect.MySQL.MySQLDialect;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DialectFactory {
	private static final List<Map<String, Dialect>> dialects = List.of(
			Map.of("H2", new H2Dialect()),
			Map.of("MySQL", new MySQLDialect()));

	public static Dialect getDialect(String dbName) {
		return dialects.stream()
				.map(x -> x.getOrDefault(dbName, null))
				.filter(Objects::nonNull)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("정의되지 않은 데이터베이스입니다."));
	}
}
