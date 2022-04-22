package infrastructure

import domain.BirthdayMessage
import domain.BirthdayMessageProvider

class BirthdayGreetingOnConsole(private val birthdayGreetingPort: BirthdayMessageProvider) {

    fun send()  {
        val birthdayMessages: Set<BirthdayMessage> =  this.birthdayGreetingPort.getMessages()
        birthdayMessages.forEach{
            println("""Happy Birthday ${it.recipientName} !""")
        }
    }

}