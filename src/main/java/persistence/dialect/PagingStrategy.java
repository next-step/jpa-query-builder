package persistence.dialect;

public interface PagingStrategy {
    String renderPagingQuery(final String query, final int offset, final int limit);
}
