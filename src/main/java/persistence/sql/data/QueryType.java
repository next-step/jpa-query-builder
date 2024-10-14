package persistence.sql.data;

/**
 * 쿼리 타입을 나타내는 열거형
 */
public enum QueryType {
    SELECT,
    SELECT_ALL,
    INSERT,
    UPDATE,
    DELETE,
    CREATE,
    DROP
}
