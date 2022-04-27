import domain.BirthdayGreeting
import domain.Clock
import infrastructure.BirthdayGreetingByMail
import infrastructure.BirthdayGreetingOnConsole
import infrastructure.FriendsFromFile
import infrastructure.MailService
import java.time.LocalDate

fun main() {

    val birthdayGreeting = fromFileToLocalMail()

    birthdayGreeting.send()
}

private fun fromFileToConsole(): BirthdayGreetingOnConsole {
    val friendRepository = FriendsFromFile("src/main/resources/friends.csv")
    val birthdayGreetingPort = BirthdayGreeting(friendRepository, clock = FakeClock())
    return BirthdayGreetingOnConsole(birthdayGreetingPort)
}

private fun fromFileToLocalMail(): BirthdayGreetingByMail {
    val friendRepository = FriendsFromFile("src/main/resources/friends.csv")
    val birthdayGreetingPort = BirthdayGreeting(friendRepository, clock = FakeClock())
    return BirthdayGreetingByMail(birthdayGreetingPort, MailService())
}

class FakeClock : Clock() {
    override fun now(): LocalDate = LocalDate.of(2022, 10, 8)
}
