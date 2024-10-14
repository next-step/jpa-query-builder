package parse

import exception.ColumnTypeUnavailableException
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.Table
import persistence.sql.ddl.column.ColumnParser
import persistence.sql.ddl.table.TableParser
import java.util.Locale

object ParseConfig {

    val TABLE_NAME_PARSER = TableParser {
        val name = it.getAnnotation(Table::class.java)?.name
            ?: it.simpleName
        name.lowercase(Locale.getDefault())
    }

    val COLUMN_NAME_PARSER = ColumnParser { field ->
        field.getAnnotation(Column::class.java)?.let { col ->
            col.name.ifEmpty { field.name }
        } ?: field.name
    }

    val COLUMN_TYPE_PARSER = ColumnParser {
        when (it.type) {
            String::class.java -> "VARCHAR(255)"
            Integer::class.java, Integer.TYPE -> "int"
            Long::class.java, java.lang.Long.TYPE -> "bigint"
            else -> throw ColumnTypeUnavailableException("사용할 수 없는 컬럼 타입입니다. [${it.type}]")
        }
    }

    val COLUMN_NULLABLE_PARSER = ColumnParser {
        if (it.isAnnotationPresent(Column::class.java) && !it.getAnnotation(Column::class.java).nullable) "NOT NULL"
        else if (it.isAnnotationPresent(Id::class.java)) "NOT NULL"
        else "DEFAULT NULL"
    }

    val ID_COLUMN_PARSER = ColumnParser { field ->
        field.getAnnotation(Id::class.java)?.let {
            "AUTO_INCREMENT, PRIMARY KEY (${field.name})"
        } ?: ""
    }
}
