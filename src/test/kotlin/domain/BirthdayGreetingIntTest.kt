package domain

import infrastructure.FriendsFromFile
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class BirthdayGreetingIntTest {

    private val friendRepository: FriendRepository = FriendsFromFile("src/test/resources/friends.csv")

    private lateinit var birthdayGreeting: BirthdayMessageProvider


    private var clock = mockk<SystemClock>()

    @BeforeEach
    internal fun setUp() {
        this.birthdayGreeting = BirthdayGreeting(friendRepository, clock = clock)
    }

    @Test
    internal fun `should get birthday greeting message for friends whose birthday is today`() {
        every { clock.now()} returns LocalDate.of(2022, 10, 8)

        val messages = this.birthdayGreeting.getMessages()

        assertThat(messages).containsExactly(BirthdayMessage("john.doe@foobar.com","John"))

    }


    @Test
    internal fun `should get birthday greeting message for friends whose birthday is February 29th and today is February 28th and year is not leap year`() {
        every { clock.now()} returns LocalDate.of(2022, 2, 28)

        val messages = this.birthdayGreeting.getMessages()

        assertThat(messages).containsExactly(BirthdayMessage("vicki.ma@test.com", "Vicki"))

    }

    @Test
    internal fun `should not get birthday greeting message for friends whose birthday is February 29th and today is February 28th and year is  leap year`() {
        every { clock.now()} returns LocalDate.of(2024, 2, 28)

        val messages = this.birthdayGreeting.getMessages()

        assertThat(messages).isEmpty()

    }
}

