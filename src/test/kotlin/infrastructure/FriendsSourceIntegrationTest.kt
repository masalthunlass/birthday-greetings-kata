package infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import domain.Friend



class FriendsSourceIntegrationTest {

    private val friendSource: FriendsFileSource =  FriendsFileSource()

    @Test
    internal fun `should get a list of friends from a file`() {
        val friends = this.friendSource.getAll()
        assertThat(friends).containsExactly(Friend("Doe", "John"))
    }
}