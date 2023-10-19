package persistence.dialect;

public class DefaultPagingStrategy extends PagingStrategy {

    private DefaultPagingStrategy() {
    }

    public static DefaultPagingStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String doPaging(final String query, final int offset, final int limit) {
        final StringBuilder pagingBuilder = new StringBuilder();
        pagingBuilder.append(query)
                .append(" limit ")
                .append(limit);
        if (offset > 0) {
            pagingBuilder.append(" offset ")
                    .append(offset);
        }
        return pagingBuilder.toString();
    }

    private static class InstanceHolder {
        private static final DefaultPagingStrategy INSTANCE = new DefaultPagingStrategy();
    }
}
