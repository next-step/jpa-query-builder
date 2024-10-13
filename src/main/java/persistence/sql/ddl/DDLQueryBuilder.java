    package persistence.sql.ddl;

    import jakarta.persistence.*;

    import java.lang.reflect.Field;
    import java.util.Arrays;
    import java.util.stream.Collectors;


    public class DDLQueryBuilder {

        private static final String CREATE_TABLE = "CREATE TABLE ";

        public static String createTable(DDLQueryBuilder ddlQueryBuilder, Class<?> entityClass) {

            if (!entityClass.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("This Class is not an Entity ");
            }

            String columns = Arrays.stream(entityClass.getDeclaredFields()).map(field -> {
                StringBuilder sb = new StringBuilder();
                String columnName = ddlQueryBuilder.getColumnName(field);
                Class<?> type = field.getType();

                sb.append(columnName).append(" ");
                sb.append(H2DBDataType.castType(type));

                extracted(ddlQueryBuilder, field, sb);

                return sb.toString();
            }).collect(Collectors.joining(", ")); // 각 필드 처리 후 콤마로 구분


            return CREATE_TABLE + entityClass.getSimpleName() + " (" + columns + ");";
        }

        private static void extracted(DDLQueryBuilder ddlQueryBuilder, Field field, StringBuilder sb) {
            if (ddlQueryBuilder.isPrimaryKey(field)) {
                sb.append(" PRIMARY KEY");
            }

            if (ddlQueryBuilder.isAutoIncrement(field)) {
                sb.append(" AUTO_INCREMENT");
            }

            if (ddlQueryBuilder.isNotNullable(field)) {
                sb.append(" NOT NULL");
            }
        }

        private boolean isAutoIncrement(Field field) {
            return field.isAnnotationPresent(GeneratedValue.class) && field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
        }

        private String getColumnName(Field field) {
            // Column 어노테이션이 존재하고 name 속성이 있으면 사용, 없으면 필드 이름 사용
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!column.name().isEmpty()) {
                    return column.name();
                }
            }
            return field.getName();
        }

        private boolean isPrimaryKey(Field field) {
            // Id 어노테이션이 존재하면 PRIMARY KEY로 처리
            return field.isAnnotationPresent(Id.class);
        }

        private boolean isNotNullable(Field field) {
            // Column 어노테이션이 존재하고 nullable 속성이 false이면 NOT NULL 처리
            Column column = field.getAnnotation(Column.class);
            return column != null && !column.nullable();
        }

    }
