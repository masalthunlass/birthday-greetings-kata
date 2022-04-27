package domain

import java.time.LocalDate
import java.time.Month

class BirthdayGreeting(private val friendRepository: FriendRepository, private val clock:Clock ): BirthdayMessageProvider {

    fun getTodayBirthdayFriends(friends: Set<Friend>, today : LocalDate): Set<Friend> = friends.filter { it.birthday.dayOfMonth == today.dayOfMonth && it.birthday.month == today.month }.toSet()

    fun getBornFeb29BirthdayFriends(friends: Set<Friend>, today : LocalDate): Set<Friend>  {
        return if (today.isLeapYear || today.month != Month.FEBRUARY || today.dayOfMonth != 28)  {
            emptySet()
        } else {
            friends.filter { it.birthday.dayOfMonth == 29 && it.birthday.month == Month.FEBRUARY }.toSet()
        }
    }

    fun toBirthdayGreetings(friends: Set<Friend>): Set<BirthdayMessage> = friends.map { BirthdayMessage(it.email, it.firstName) }.toSet()

    override fun getMessages(): Set<BirthdayMessage> {
        val friends = friendRepository.getAll()
        val todayBirthdayFriends = getTodayBirthdayFriends(friends, clock.now())
        val bornFeb29BirthdayFriends = getBornFeb29BirthdayFriends(friends, clock.now())
       return toBirthdayGreetings(todayBirthdayFriends + bornFeb29BirthdayFriends)
    }
}