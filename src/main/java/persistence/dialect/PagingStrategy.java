package persistence.dialect;

import persistence.exception.PersistenceException;

public interface PagingStrategy {
    String renderPagingQuery(final String query, final int offset, final int limit);

    default void validateLimit(final int limit) {
        if(limit < 0) {
            throw new PersistenceException("limit 은 0보다 작을 수 없습니다.");
        }
    }

    default void validateOffset(final int offset) {
        if(offset < 0) {
            throw new PersistenceException("offset 은 0보다 작을 수 없습니다.");
        }
    }
}
