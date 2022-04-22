package infrastructure

import domain.BirthdayMessageProvider

class BirthdayGreetingByMail(private val birthdayGreetingPort: BirthdayMessageProvider) {

    fun send(): Unit = TODO()


}