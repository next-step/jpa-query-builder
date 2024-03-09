package persistence.sql.ddl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import domain.Person;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

public class CreateQueryBuilder {
	Map<? extends Class<? extends Serializable>, String> javaClassTypeToDbTypes = Map.of(
		Long.class, "BIGINT",
		String.class, "VARCHAR",
		Integer.class, "BIGINT"
	);

	public String getCreateTableSql() throws
		NoSuchMethodException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {

		Class<Person> personClass = Person.class;

		Person person = personClass.getConstructor().newInstance();

		// 목표 출력의 목표를 스트링으로 적는다
		/*
			CREATE TABLE person (
    			id INT AUTO_INCREMENT PRIMARY KEY,
    			name VARCHAR(50),
    			age INT,
    			email VARCHAR(50) NOT NULL,
    			index INT
			);
		 */
		// 메서드 분리 (3)
		Field idField = Arrays.stream(getDeclaredFields(personClass))
			.filter(x -> x.isAnnotationPresent(GeneratedValue.class))
			.findFirst().get();

		String idFieldName = idField.getName();

		String idType = javaClassTypeToDbTypes.get(idField.getType());

		String generatedValue = generateValue();

		String primaryKey = getPrimaryKey();

		String idCombi = String.format("%s %s %s %s,", idFieldName, idType, generatedValue, primaryKey);

		// name VARCHAR(50),
		Field nameField = Arrays.stream(getDeclaredFields(personClass))
			.filter(x -> x.isAnnotationPresent(Column.class)).findFirst().get();

		String name = nameField.getName();

		String nameType = javaClassTypeToDbTypes.get(nameField.getType());

		String nameCombi = String.format("%s %s,", name, nameType);

		// age INT,
		Field ageField = Arrays.stream(getDeclaredFields(personClass))
			.filter(x -> x.isAnnotationPresent(Column.class))
			.filter(x -> x.getAnnotation(Column.class).name().equals("old"))
			.findFirst().get();
		String age = ageField.getName();
		String ageType = javaClassTypeToDbTypes.get(ageField.getType());

		String ageCombi = String.format("%s %s,", age, ageType);

		// email VARCHAR(50) NOT NULL,
		Field emailField = Arrays.stream(getDeclaredFields(personClass))
			.filter(x -> x.isAnnotationPresent(Column.class))
			.filter(x -> !x.getAnnotation(Column.class).nullable())
			.findFirst().get();
		String email = emailField.getName();
		String emailType = javaClassTypeToDbTypes.get(emailField.getType());
		boolean isNullable = emailField.getAnnotation(Column.class).nullable();

		String emialNullable = isNull(isNullable);

		String emailCombi = String.format("%s %s %s", email, emailType, emialNullable);

		// index INT
		Field indexField = Arrays.stream(getDeclaredFields(personClass))
			.filter(x -> x.isAnnotationPresent(Transient.class))
			.findFirst().get();
		String indexCombi = "";

		String tableName = personClass.getSimpleName();

		// List<? extends Class<?>> fieldTypes = Arrays.stream(getDeclaredFields(personClass)).map(Field::getType).toList();
		//
		// List<String> fieldNames = Arrays.stream(getDeclaredFields(personClass)).map(Field::getName).toList();

		String createTableSql = String.format("create table %s(%s %s %s %s %s)",
			tableName,
			idCombi,
			nameCombi,
			ageCombi,
			emailCombi,
			indexCombi
		);

		return createTableSql;
	}

	private String getPrimaryKey() {

		return "PRIMARY KEY";
	}

	private static Field[] getDeclaredFields(Class<Person> personClass) {
		return personClass.getDeclaredFields();
	}

	//  DB에서 가져와야겠다 ai를 //
	private static String generateValue() {
		return "AUTO_INCREMENT";
	}

	private  static String isNull(boolean isNullable) {
		if (!isNullable) {
			return "NOT NULL";
		}
		return "";
	}
}
