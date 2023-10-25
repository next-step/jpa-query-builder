package persistence.dialect;

public interface Dialect {
	String getColumnType(Class<?> typeClass);

	String getGeneratedStrategy(String generatedTypeName);
}
