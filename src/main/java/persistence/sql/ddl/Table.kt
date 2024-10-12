package persistence.sql.ddl

class Table (
    private val clazz: Class<*>
) {
    private val columns: List<Column> = extractColumn(clazz)

    fun createQuery(): String {
        val tableName = this.clazz.simpleName.lowercase()
        return "CREATE TABLE $tableName (${columnsToQuery()})"
    }

    fun dropQuery() = "DROP TABLE ${this.clazz.simpleName.lowercase()}"

    private fun columnsToQuery(): String = columns.map { it.createQuery() }
        .filter { it.isNotEmpty() }
        .joinToString(",")

    private fun extractColumn(clazz: Class<*>): List<Column> =
        clazz.declaredFields.map { Column(it) }

}
