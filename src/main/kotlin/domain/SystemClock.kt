package domain

import java.time.LocalDate

interface Clock{
    fun now(): LocalDate
}

class SystemClock : Clock {
    override fun now(): LocalDate = LocalDate.now()
}