package domain

import factories.aFriend
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BirthdayGreetingTest {

    private val today = LocalDate.of(2022,4,22)

    @Test
    internal fun `should give firstnames and contact of those of your friends which have their birthday today`() {

        val notTodayBirthday = LocalDate.of(2022,5,22)
        val todayBirthday = today
        val friends = setOf(
            aFriend(birthday = notTodayBirthday, email = "Mary@email.test", firstName = "Mary"),
            aFriend(birthday = todayBirthday, email = "John@email.test", firstName = "John"))

        val todayBirthdayFriends = getTodayBirthdayFriends(friends, today)

        assertThat(todayBirthdayFriends).containsExactly(
            aFriend(birthday = todayBirthday, email = "John@email.test", firstName = "John")
        )
    }
}

