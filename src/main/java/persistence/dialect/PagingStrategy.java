package persistence.dialect;

import persistence.exception.PersistenceException;

public abstract class PagingStrategy {
    public final String renderPagingQuery(final String query, final int offset, final int limit) {
        validateOffset(offset);
        validateLimit(limit);
        return doPaging(query, offset, limit);
    }

    abstract protected String doPaging(final String query, final int offset, final int limit);

    private void validateLimit(final int limit) {
        if(limit < 0) {
            throw new PersistenceException("limit 은 0보다 작을 수 없습니다.");
        }
    }

    private void validateOffset(final int offset) {
        if(offset < 0) {
            throw new PersistenceException("offset 은 0보다 작을 수 없습니다.");
        }
    }
}
