package domain

import java.time.LocalDate

data class Friend(
    val birthday: LocalDate,
    val firstName: String,
    val email: String
)
