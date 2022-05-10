package domain

import FakeClock
import factories.aFriend
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BirthdayGreetingTest {


    private val friendRepository: FriendRepository = mockk()
    private val today = LocalDate.of(2022, 4, 22)



    @Test
    internal fun `should give firstnames and contact of those of your friends which have their birthday today`() {

        val birthdayGreeting = BirthdayGreeting(friendRepository,  FakeClock())
        val notTodayBirthday = LocalDate.of(1982, 5, 22)
        val todayBirthday = LocalDate.of(1982, 4, 22)
        val friends = setOf(
            aFriend(birthday = notTodayBirthday, email = "Mary@email.test", firstName = "Mary"),
            aFriend(birthday = todayBirthday, email = "John@email.test", firstName = "John")
        )

        val todayBirthdayFriends = birthdayGreeting.getTodayBirthdayFriends(friends, today)

        assertThat(todayBirthdayFriends).containsExactly(
            aFriend(birthday = todayBirthday, email = "John@email.test", firstName = "John")
        )
    }

    @Test
    internal fun `should create birthday greetings message from a list of friends`() {
        val birthdayGreeting = BirthdayGreeting(friendRepository,  FakeClock())
        val friendsToGreet = setOf(
            aFriend(email = "John@email.test", firstName = "John")
        )

        val birthdayGreetings = birthdayGreeting.toBirthdayGreetings(friendsToGreet)

        assertThat(birthdayGreetings).containsExactly(
            BirthdayMessage(contact = "John@email.test", recipientName = "John")
        )
    }

    @Test
    internal fun `should give firstnames and contact of those of your friends which have their birthday on February 29th and this year not leapYear`() {

        val notLeapYear = LocalDate.of(2022, 2, 28)
        val birthdayGreeting = BirthdayGreeting(friendRepository,  FakeClock(notLeapYear))
        val birthdate = LocalDate.of(2000, 2, 29)

        val friends = setOf(
            aFriend(birthday = birthdate, email = "Mary@email.test", firstName = "Mary")
        )
        every{friendRepository.getAll()}.returns(friends)

        val todayBirthdayFriends = birthdayGreeting.getMessages()

        assertThat(todayBirthdayFriends).containsExactly(
            BirthdayMessage(contact = "Mary@email.test", recipientName = "Mary")
        )
    }

    @Test
    internal fun `should not give firstname and contact of those of your friends which have their birthday on February 29th and this year is leapYear`() {
        val leapYear = LocalDate.of(2024, 2, 28)
        val birthdate = LocalDate.of(2000, 2, 29)

        val birthdayGreeting = BirthdayGreeting(friendRepository,  FakeClock(leapYear))

        val friends = setOf(
            aFriend(birthday = birthdate, email = "Mary@email.test", firstName = "Mary")
        )

         every{friendRepository.getAll()}.returns(friends)

        val todayBirthdayFriends = birthdayGreeting.getMessages()

        assertThat(todayBirthdayFriends).isEmpty()
    }

    @Test
    internal fun `should not give firstname and contact of those of your friends which have their birthday on February 29th and this year is not leapYear and this is not Feb 28th`() {
        val leapYear = LocalDate.of(2024, 8, 28)
        val birthdate = LocalDate.of(2000, 2, 29)
        val birthdayGreeting = BirthdayGreeting(friendRepository,  FakeClock(leapYear))

        val friends = setOf(
            aFriend(birthday = birthdate, email = "Mary@email.test", firstName = "Mary")
        )
        every{friendRepository.getAll()}.returns(friends)

        val todayBirthdayFriends = birthdayGreeting.getMessages()

        assertThat(todayBirthdayFriends).isEmpty()
    }


}


