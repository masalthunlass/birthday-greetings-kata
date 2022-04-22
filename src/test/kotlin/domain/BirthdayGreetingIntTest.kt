package domain

import FakeClock
import infrastructure.FriendsFromFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BirthdayGreetingIntTest {

    private val friendRepository: FriendRepository = FriendsFromFile("src/test/resources/friends.csv")

    private lateinit var birthdayGreeting: BirthdayMessageProvider

    @BeforeEach
    internal fun setUp() {
        this.birthdayGreeting = BirthdayGreeting(friendRepository, clock = FakeClock())
    }

    @Test
    internal fun `should get birthday greeting message for friends whose birthday is today`() {

        val message = this.birthdayGreeting.getMessages()

        assertThat(message).containsExactly(BirthdayMessage("John","john.doe@foobar.com"))

    }
}

