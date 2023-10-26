package persistence.dialect.H2;

import persistence.dialect.Dialect;

public class H2Dialect implements Dialect {
	@Override
	public String getColumnType(Class<?> typeClass) {
		return H2ColumnType.getColumnTypeByClass(typeClass);
	}

	@Override
	public String getGeneratedStrategy(String generatedTypeName) {
		return H2GeneratedType.getStrategyByTypeName(generatedTypeName);
	}
}
