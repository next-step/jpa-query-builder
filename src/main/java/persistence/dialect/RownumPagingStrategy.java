package persistence.dialect;

public class RownumPagingStrategy extends PagingStrategy {

    private RownumPagingStrategy() {
    }

    public static RownumPagingStrategy getInstance() {
        return RownumPagingStrategy.InstanceHolder.INSTANCE;
    }

    @Override
    public String doPaging(final String query, final int offset, final int limit) {
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

