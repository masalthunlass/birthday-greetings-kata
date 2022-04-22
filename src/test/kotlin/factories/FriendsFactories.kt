package factories

import domain.Friend
import java.time.LocalDate

fun aFriend(
    birthday: LocalDate = LocalDate.MAX,
    firstName: String = "",
    email: String = ""
):Friend = Friend(
    birthday,
    firstName,
    email
)