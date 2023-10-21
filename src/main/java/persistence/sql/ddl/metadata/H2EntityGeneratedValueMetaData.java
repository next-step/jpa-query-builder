package persistence.sql.ddl.metadata;

import jakarta.persistence.GeneratedValue;
import java.lang.reflect.Field;

public class H2EntityGeneratedValueMetaData implements EntityMetaData {

	private static final String GENERATED_VALUE_META_DATA = " AUTO_INCREMENT";

	@Override
	public String getMetaDateQuery(Field field) {
		GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
		if (generatedValue == null) {
			return "";
		}
		return GENERATED_VALUE_META_DATA;
	}
}
