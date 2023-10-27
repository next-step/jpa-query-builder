package persistence.sql.dml.clause;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class ChainingWhereClauses implements Iterable<ChainingLogicalOperatorStandardWhereClause> {
    private List<ChainingLogicalOperatorStandardWhereClause> whereClauses;

    public ChainingWhereClauses() {
        this.whereClauses = new ArrayList<>();
    }

    public void add(ChainingLogicalOperatorStandardWhereClause whereClause) {
        whereClauses.add(whereClause);
    }

    public boolean isEmpty() {
        return whereClauses.isEmpty();
    }

    @Override
    public Iterator<ChainingLogicalOperatorStandardWhereClause> iterator() {
        return whereClauses.iterator();
    }

    @Override
    public Spliterator<ChainingLogicalOperatorStandardWhereClause> spliterator() {
        return whereClauses.spliterator();
    }

    public Stream<ChainingLogicalOperatorStandardWhereClause> stream() {
        return whereClauses.stream();
    }
}
