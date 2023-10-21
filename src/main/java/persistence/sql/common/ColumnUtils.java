package persistence.sql.common;

import persistence.sql.ddl.Column;
import persistence.sql.dml.Value;
import persistence.sql.dml.Values;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnUtils {
	public static List<Column> convertClassToColumnList(Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.map(Column::new)
				.collect(Collectors.toList());
	}

	public static Values convertObjectToValues(Object object) {
		Values values = new Values(new ArrayList<>());
		Field[] fields = object.getClass().getDeclaredFields();

		for(Field field : fields) {
			field.setAccessible(true);
			values.addValue(new Value(field, object));
		}

		return values;
	}
}
