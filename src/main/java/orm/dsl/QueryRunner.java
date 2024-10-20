package orm.dsl;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;

import java.util.List;

public class QueryRunner {

    private final JdbcTemplate jdbcTemplate;

    public QueryRunner() {
        this.jdbcTemplate = null;
    }

    public QueryRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <E> E fetchOne(String sql, RowMapper<E> rowMapper) {
        throwIfNoJdbcTemplate();
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    public <E> List<E> fetch(String sql, RowMapper<E> rowMapper) {
        throwIfNoJdbcTemplate();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void execute(String sql) {
        throwIfNoJdbcTemplate();
        jdbcTemplate.execute(sql);
    }

    /**
     * 데이터 write 후 생성된 키를 반환한다.
     * @param sql 실행쿼리
     * @return 생성된 키 (Long이 아닌 이유는 PostgreSQL의 경우 문자열 UUID를 생성하기도 하기 때문)
     */
    public Object executeUpdateWithReturningKey(String sql) {
        throwIfNoJdbcTemplate();
        return jdbcTemplate.executeUpdateWithReturningGenKey(sql);
    }

    private void throwIfNoJdbcTemplate() {
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate is not set");
        }
    }
}
