package persistence.sql.ddl

import jakarta.persistence.Entity

class TableManager (
    classes: List<Class<*>>
) {
    private val entities = classes.filter { it.isAnnotationPresent(Entity::class.java) }
    constructor(vararg clazz: Class<*>): this(clazz.toList())

    fun createQuery(): List<String> {
        return entities.map { Table(it).createQuery() }
    }

    fun dropQuery(): List<String> {
        return entities.map { Table(it).dropQuery() }
    }
}
