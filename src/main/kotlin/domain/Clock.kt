package domain

import java.time.LocalDate

open class Clock {
    open fun now(): LocalDate = LocalDate.now()
}