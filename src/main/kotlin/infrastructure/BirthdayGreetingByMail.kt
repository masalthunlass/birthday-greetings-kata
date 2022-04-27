package infrastructure

import domain.BirthdayMessageProvider

class BirthdayGreetingByMail(private val birthdayGreetingPort: BirthdayMessageProvider,
                             private val mailService: MailService) {

    fun send()  {
        this.birthdayGreetingPort.getMessages().forEach {
            this.mailService.send(it.contact, """Happy Birthday ${it.recipientName} !""", "it's a special day")
        }
    }

}