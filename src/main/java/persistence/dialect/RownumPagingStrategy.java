package persistence.dialect;

public class RownumPagingStrategy implements PagingStrategy {

    private RownumPagingStrategy() {
    }

    public static RownumPagingStrategy getInstance() {
        return RownumPagingStrategy.InstanceHolder.INSTANCE;
    }

    @Override
    public String renderPagingQuery(final String query, final int offset, final int limit) {
        validateOffset(offset);
        validateLimit(limit);
        final StringBuilder pagingBuilder = new StringBuilder();
        pagingBuilder
                .append("select * from (select row.*, rownum as rnum from (")
                .append(query)
                .append(") row) where rnum > ")
                .append(offset)
                .append(" and rnum <= ")
                .append(offset + limit);
        return pagingBuilder.toString();
    }

    private static class InstanceHolder {
        private static final RownumPagingStrategy INSTANCE = new RownumPagingStrategy();
    }

}

