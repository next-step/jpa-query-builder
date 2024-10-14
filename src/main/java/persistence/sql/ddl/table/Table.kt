package persistence.sql.ddl.table

import parse.ParseType
import parse.ParseType.NAME
import persistence.sql.find

class Table (
    private val tableParsers: Map<ParseType, TableParser>,
    private val clazz: Class<*>
) {


    fun createQuery(): String {
        return "CREATE TABLE ${tableParsers.find(NAME).parse(clazz)}"
    }

    fun dropQuery(): String {
        return "DROP TABLE ${tableParsers.find(NAME).parse(clazz)}"
    }
}
