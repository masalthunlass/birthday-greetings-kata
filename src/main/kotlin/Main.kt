import domain.BirthdayGreeting
import domain.Clock
import infrastructure.BirthdayGreetingOnConsole
import infrastructure.FriendsFromFile
import java.time.LocalDate

fun main() {

    val friendRepository = FriendsFromFile("src/main/resources/friends.csv")
    val birthdayGreetingPort = BirthdayGreeting(friendRepository, clock = FakeClock())
    val birthdayGreetingOnConsole = BirthdayGreetingOnConsole(birthdayGreetingPort)

    birthdayGreetingOnConsole.send();
}

class FakeClock : Clock() {
    override fun now(): LocalDate = LocalDate.of(2022, 10, 8)
}
