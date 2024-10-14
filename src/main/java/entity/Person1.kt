package entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Transient

@Entity
class Person1 (
    @Id
    private val id: Long = 0L,
    private var name: String = "",
    private var age: Int = -1,
)
