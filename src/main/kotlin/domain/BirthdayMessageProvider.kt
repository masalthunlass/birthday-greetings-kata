package domain

interface BirthdayMessageProvider {

    fun getMessages(): Set<BirthdayMessage>
}