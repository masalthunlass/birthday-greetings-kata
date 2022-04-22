package infrastructure

import factories.aFriend
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class FriendsFromFileIntTest {

    private val friendSource: FriendsFromFile = FriendsFromFile("src/test/resources/friends.csv")

    @Test
    internal fun `should get a list of friends from a file`() {
        val friends = this.friendSource.getAll()
        assertThat(friends).containsExactly(
            aFriend(LocalDate.of(1982, 10, 8), "John", "john.doe@foobar.com"))
    }
}