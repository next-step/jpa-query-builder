package persistence.sql.ddl.column

import java.lang.reflect.Field

@FunctionalInterface
fun interface ColumnParser {
    fun parse(field: Field): String
}
