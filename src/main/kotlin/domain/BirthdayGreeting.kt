package domain

import java.time.LocalDate
import java.time.Month

class BirthdayGreeting(private val friendRepository: FriendRepository, private val clock: Clock) : BirthdayMessageProvider {

    fun getTodayBirthdayFriends(friends: Set<Friend>, today: LocalDate): Set<Friend> =
        friends.filter { it.birthday.dayOfMonth == today.dayOfMonth && it.birthday.month == today.month }.toSet()

   fun Set<Friend>.getBornFeb29BirthdayFriends(): Set<Friend> = filter { friend -> friend.wasBornOn29thFeb() }.toSet()

    private fun LocalDate.yearHas29thFeb() = isLeapYear || month != Month.FEBRUARY || dayOfMonth != 28

    private fun Friend.wasBornOn29thFeb() = birthday.dayOfMonth == 29 && birthday.month == Month.FEBRUARY

    fun toBirthdayGreetings(friends: Set<Friend>): Set<BirthdayMessage> =
        friends.map { BirthdayMessage(it.email, it.firstName) }.toSet()

    override fun getMessages(): Set<BirthdayMessage> {
        val friends = friendRepository.getAll()
        val today = clock.now()

        val todayBirthdayFriends = getTodayBirthdayFriends(friends, today).plus(
            if (!today.yearHas29thFeb()) {
                friends.getBornFeb29BirthdayFriends()
            } else {
                emptyList()
            }
        )

        return toBirthdayGreetings(todayBirthdayFriends)
    }
}