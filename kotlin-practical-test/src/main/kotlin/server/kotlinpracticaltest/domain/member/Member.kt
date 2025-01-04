package server.kotlinpracticaltest.domain.member

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn

@Entity
class Member(
    name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String = name

    @Embedded
    @Column(name = "address", nullable = false)
    var homeAddress: Address? = null
        protected set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hobby", joinColumns = [JoinColumn(name = "member_id")])
    @Column(name = "hobby", nullable = false)
    @Enumerated(EnumType.STRING)
    var hobbies: MutableCollection<Hobby> = mutableListOf()
        protected set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "favorite_food", joinColumns = [JoinColumn(name = "member_id")])
    @Column(name = "food_name", nullable = false)
    var favoriteFoods: MutableSet<String> = mutableSetOf()
        protected set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "address", joinColumns = [JoinColumn(name = "member_id")])
    @Column(name = "address", nullable = false)
    var addressHistory: MutableList<Address> = mutableListOf()
        protected set

    fun applyHomeAddress(address: Address) {
        this.homeAddress = address
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
