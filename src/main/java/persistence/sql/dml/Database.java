package persistence.sql.dml;

import java.sql.ResultSet;
import java.util.function.Function;

/**
 * 데이터베이스를 나타내는 인터페이스
 */
public interface Database {
    /**
     * INSERT, UPDATE, DELETE 등의 쿼리를 실행한다.
     *
     * @param query 실행할 쿼리
     */
    void executeUpdate(String query);


    ResultSet executeQuery(String query);

    /**
     * SELECT 쿼리를 실행하고 결과를 반환한다.
     *
     * @param query                실행할 쿼리
     * @param mapRowMapperFunction ResultSet을 매핑하는 함수
     * @return 쿼리 실행 결과
     */
    <T> T executeQuery(String query, Function<ResultSet, T> mapRowMapperFunction);
}
