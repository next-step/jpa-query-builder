package persistence.sql.ddl.table

@FunctionalInterface
fun interface TableParser {
    fun parse(clazz: Class<*>): String
}
