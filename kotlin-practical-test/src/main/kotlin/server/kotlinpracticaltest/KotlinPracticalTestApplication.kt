package server.kotlinpracticaltest

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.Transactional
import server.kotlinpracticaltest.domain.member.Address
import server.kotlinpracticaltest.domain.member.Hobby
import server.kotlinpracticaltest.domain.member.Member
import server.kotlinpracticaltest.domain.member.MemberRepository

@Transactional
@EnableJpaAuditing
@SpringBootApplication
class KotlinPracticalTestApplication(
    private val memberRepository: MemberRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val member = Member("이름")
        memberRepository.save(member)

        val homeAddress = Address("고양시", "일산동구", "11111")
        member.applyHomeAddress(homeAddress)

        member.hobbies.add(Hobby.PLAY_GAME)
        member.hobbies.add(Hobby.EXERCISE)

        val favoriteFoods = mutableSetOf("삼겹살", "피자")
        member.favoriteFoods.addAll(favoriteFoods)

        val oldAddress1 = Address("고양시", "일산동구", "11111")
        val oldAddress2 = Address("파주시", "문산읍", "98765")
        member.addressHistory.add(oldAddress1)
        member.addressHistory.add(oldAddress2)

        member.addressHistory.remove(Address("고양시", "일산동구", "11111"))
        member.addressHistory.add(Address("서울시", "중구", "55555"))

//        memberRepository.delete(member)
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinPracticalTestApplication>(*args)
}
