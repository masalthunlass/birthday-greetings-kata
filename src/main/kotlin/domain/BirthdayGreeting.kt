package domain

import java.time.LocalDate

class BirthdayGreeting(private val friendRepository: FriendRepository, private val clock:Clock ): BirthdayMessageProvider {

    fun getTodayBirthdayFriends(friends: Set<Friend>, today : LocalDate): Set<Friend> = friends.filter { it.birthday.dayOfMonth == today.dayOfMonth && it.birthday.month == today.month }.toSet()

    fun toBirthdayGreetings(friends: Set<Friend>): Set<BirthdayMessage> = friends.map { BirthdayMessage(it.email, it.firstName) }.toSet()

    override fun getMessages(): Set<BirthdayMessage> {
        val friends = friendRepository.getAll()
        val todayBirthdayFriends = getTodayBirthdayFriends(friends, clock.now())
       return toBirthdayGreetings(todayBirthdayFriends)
    }
}