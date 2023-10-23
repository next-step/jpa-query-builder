package persistence.sql.dml;

import persistence.sql.metadata.Value;
import persistence.sql.metadata.Values;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

public class WhereClauseBuilder {
	private final static String WHERE_CLAUSE = " WHERE %s";

	private final Values values;

	public WhereClauseBuilder(Class<?> clazz, List<String> whereColumns, List<String> whereValues) {
		if(whereColumns.size() != whereValues.size()) {
			throw new IllegalArgumentException("조건문에 해당하는 컬럼과 값의 개수가 일치하지 않습니다.");
		}

		this.values = new Values(
				IntStream.range(0, whereColumns.size())
						.mapToObj(index -> {
							try {
								return new Value(clazz.getDeclaredField(whereColumns.get(index)), whereValues.get(index));
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						})
						.collect(Collectors.toList())
		);
	}

	public String buildClause() {
		if(values.buildWhereClause().isEmpty()) {
			return "";
		}

		return format(WHERE_CLAUSE, values.buildWhereClause());
	}
}
