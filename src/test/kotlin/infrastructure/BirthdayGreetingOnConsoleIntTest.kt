package infrastructure

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import domain.BirthdayGreeting
import domain.SystemClock
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate


class BirthdayGreetingOnConsoleIntTest {

    private val friendRepository = FriendsFromFile("src/test/resources/friends.csv")
    private val clock = mockk<SystemClock>()
    private val birthdayGreetingPort = BirthdayGreeting(friendRepository, clock = clock)
    private val birthdayGreetingOnConsole: BirthdayGreetingOnConsole = BirthdayGreetingOnConsole(birthdayGreetingPort)


    @Test
    internal fun `should send out birthday message to console`() {
        givenTodayIs(2022, 10, 8)

        val output = tapSystemOut { this.birthdayGreetingOnConsole.send() }

        assertThat("Happy Birthday John !").isEqualTo(output.trim());

    }

    private fun givenTodayIs(year: Int, month: Int, day: Int) {
        every { clock.now() } returns LocalDate.of(year, month, day)
    }
}