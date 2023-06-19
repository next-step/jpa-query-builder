package persistence.dialect;

public interface Dialect {
    boolean support(String dbType);

    String getType(Class<?> type);

}
