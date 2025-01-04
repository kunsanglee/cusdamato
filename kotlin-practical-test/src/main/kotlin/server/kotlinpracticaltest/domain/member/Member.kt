package server.kotlinpracticaltest.domain.member

import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "member", indexes = [Index(name = "idx_member", columnList = "id", unique = true)])
class Member(
    name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String = name

    @Embedded
    var homeAddress: Address? = null
        protected set

    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JoinColumn(name = "member_id")
    var hobbies: MutableCollection<HobbyEntity> = mutableListOf()
        protected set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "favorite_food", joinColumns = [JoinColumn(name = "member_id")])
    @Column(name = "food_name", nullable = false)
    var favoriteFoods: MutableSet<String> = mutableSetOf()
        protected set

    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JoinColumn(name = "member_id")
    var addressHistory: MutableList<AddressEntity> = mutableListOf()
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
