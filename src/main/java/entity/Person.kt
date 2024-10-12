package entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Person (
    @Id
    val id: Long = 0L,
    var name: String = "",
    var age: Int = -1
)
