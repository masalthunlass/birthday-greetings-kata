import domain.BirthdayGreeting
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
    val birthdayGreetingPort = BirthdayGreeting(friendRepository, clock = FakeClock(LocalDate.of(2022, 10, 8)))
    return BirthdayGreetingOnConsole(birthdayGreetingPort)
}

private fun fromFileToLocalMail(): BirthdayGreetingByMail {
    val friendRepository = FriendsFromFile("src/main/resources/friends.csv")
    val birthdayGreetingPort = BirthdayGreeting(friendRepository, clock = FakeClock(LocalDate.of(2022, 10, 8)))
    return BirthdayGreetingByMail(birthdayGreetingPort, MailService())
}

