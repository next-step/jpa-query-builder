package entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Transient

@Entity
class Person (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,

    @Column(name = "nick_name")
    private var name: String = "",

    @Column(name = "old")
    private var age: Int = -1,

    @Column(nullable = false)
    private var email: String = "",

    @Transient
    private var index: Int = 0
)
