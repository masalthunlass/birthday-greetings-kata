package domain

import factories.aFriend
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class BirthdayGreetingTest {
    @MockK
    private lateinit var clock: Clock

    @MockK
    private lateinit var friendRepository: FriendRepository

    @InjectMockKs
    private lateinit var birthdayGreeting: BirthdayGreeting

    private val today = LocalDate.of(2022, 4, 22)

    @BeforeEach
    internal fun setUp() {
        every { clock.now() } returns today
    }

    @Test
    internal fun `should give firstnames and contact of those of your friends which have their birthday today`() {

        val notTodayBirthday = LocalDate.of(1982, 5, 22)
        val todayBirthday = LocalDate.of(1982, 4, 22)
        val friends = setOf(
            aFriend(birthday = notTodayBirthday, email = "Mary@email.test", firstName = "Mary"),
            aFriend(birthday = todayBirthday, email = "John@email.test", firstName = "John")
        )

        val todayBirthdayFriends = this.birthdayGreeting.getTodayBirthdayFriends(friends, today)

        assertThat(todayBirthdayFriends).containsExactly(
            aFriend(birthday = todayBirthday, email = "John@email.test", firstName = "John")
        )
    }

    @Test
    internal fun `should create birthday greetings message from a list of friends`() {

        val friendsToGreet = setOf(
            aFriend(email = "John@email.test", firstName = "John")
        )

        val birthdayGreetings = this.birthdayGreeting.toBirthdayGreetings(friendsToGreet)

        assertThat(birthdayGreetings).containsExactly(
            BirthdayMessage(contact = "John@email.test", recipientName = "John")
        )
    }

    @Test
    internal fun `should give firstnames and contact of those of your friends which have their birthday on February 29th and this year not leapYear`() {
        val notLeapYear = LocalDate.of(2022, 2, 28)
        val birthdate = LocalDate.of(2000, 2, 29)

        val friends = setOf(
            aFriend(birthday = birthdate, email = "Mary@email.test", firstName = "Mary")
        )

        val todayBirthdayFriends = this.birthdayGreeting.getBornFeb29BirthdayFriends(friends, notLeapYear)

        assertThat(todayBirthdayFriends).containsExactly(
            aFriend(birthday = birthdate, email = "Mary@email.test", firstName = "Mary")
        )
    }

    @Test
    internal fun `should not give firstname and contact of those of your friends which have their birthday on February 29th and this year is leapYear`() {
        val leapYear = LocalDate.of(2024, 2, 28)
        val birthdate = LocalDate.of(2000, 2, 29)

        val friends = setOf(
            aFriend(birthday = birthdate, email = "Mary@email.test", firstName = "Mary")
        )

        val todayBirthdayFriends = this.birthdayGreeting.getBornFeb29BirthdayFriends(friends, leapYear)

        assertThat(todayBirthdayFriends).isEmpty()
    }

    @Test
    internal fun `should not give firstname and contact of those of your friends which have their birthday on February 29th and this year is not leapYear and this is not Feb 28th`() {
        val leapYear = LocalDate.of(2024, 8, 28)
        val birthdate = LocalDate.of(2000, 2, 29)

        val friends = setOf(
            aFriend(birthday = birthdate, email = "Mary@email.test", firstName = "Mary")
        )

        val todayBirthdayFriends = this.birthdayGreeting.getBornFeb29BirthdayFriends(friends, leapYear)

        assertThat(todayBirthdayFriends).isEmpty()
    }


}


