package persistence.sql.ddl

import jakarta.persistence.Entity

class TableManager (
    private val classes: List<Class<*>>
) {
    constructor(vararg clazz: Class<*>): this(clazz.toList())

    fun createQuery() = classes.filter { it.isAnnotationPresent(Entity::class.java) }
        .map { Table(it).toQuery() }

}
