package persistence.dialect.MySQL;

import persistence.dialect.Dialect;

public class MySQLDialect implements Dialect {
	@Override
	public String getColumnType(Class<?> typeClass) {
		return MySQLColumnType.getColumnTypeByClass(typeClass);
	}

	@Override
	public String getGeneratedStrategy(String generatedTypeName) {
		return MySQLGeneratedType.getStrategyByTypeName(generatedTypeName);
	}
}
