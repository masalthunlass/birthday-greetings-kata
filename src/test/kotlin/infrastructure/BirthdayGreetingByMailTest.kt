package infrastructure

import domain.BirthdayGreeting
import domain.Clock
import domain.Friend
import domain.FriendRepository
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate


class BirthdayGreetingByMailTest {

    private val friendRepository = mockk<FriendRepository>()
    private val clock = mockk<Clock>()
    private val birthdayGreetingPort = BirthdayGreeting(friendRepository, clock = clock)
    private val mailService = mockk<MailService>(relaxUnitFun = true)
    private val birthdayGreetingByMail: BirthdayGreetingByMail =
        BirthdayGreetingByMail(birthdayGreetingPort, mailService)

    @Test
    internal fun `should send out birthday message to each contact found`() {
        givenTodayIs(2022, 10, 8)
        givenTodayBirthdayFriendsAre(
            Friend(LocalDate.of(1985, 10, 8), "John", "john.doe@foobar.com"),
            Friend(LocalDate.of(1966, 10, 8), "Thomas", "tom@test.com")
        )

        this.birthdayGreetingByMail.send()

        verify(exactly = 2 ) { mailService.send(any(), any(), any()) }

    }


    @Test
    internal fun `should send out birthday message to recipient email`() {
        givenTodayIs(2022, 10, 8)
        givenTodayBirthdayFriendsAre(
            Friend(LocalDate.of(1985, 10, 8), "John", "john.doe@foobar.com")
        )

        val recipientCaptor = slot<String>()
        justRun { mailService.send(capture(recipientCaptor), any(), any()) }

        this.birthdayGreetingByMail.send()

        assertThat(recipientCaptor.captured).isEqualTo("john.doe@foobar.com");

    }


    @Test
    internal fun `should send out birthday message `() {
        givenTodayIs(2022, 10, 8)
        givenTodayBirthdayFriendsAre(
            Friend(LocalDate.of(1985, 10, 8), "John", "john.doe@foobar.com")
        )

        val messageCaptor = slot<String>()
        justRun { mailService.send(any(), capture(messageCaptor), any()) }

        this.birthdayGreetingByMail.send()

        assertThat(messageCaptor.captured).isEqualTo("Happy Birthday John !");

    }

    @Test
    internal fun `should send out birthday message subject it's a special day `() {
        givenTodayIs(2022, 10, 8)
        givenTodayBirthdayFriendsAre(
            Friend(LocalDate.of(1985, 10, 8), "John", "john.doe@foobar.com")
        )

        val subjectCaptor = slot<String>()
        justRun { mailService.send(any(), any(), capture(subjectCaptor)) }

        this.birthdayGreetingByMail.send()

        assertThat(subjectCaptor.captured).isEqualTo("it's a special day");

    }


    private fun givenTodayBirthdayFriendsAre(vararg friends: Friend) {
        every { friendRepository.getAll() } returns friends.toSet()
    }

    private fun givenTodayIs(year: Int, month: Int, day: Int) =
        every { clock.now() } returns LocalDate.of(year, month, day)

}