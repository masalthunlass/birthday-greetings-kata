package domain

import java.time.LocalDate

fun getTodayBirthdayFriends(friends: Set<Friend>, today : LocalDate): Set<Friend> = friends.filter { it.birthday == today }.toSet()
