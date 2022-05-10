import domain.Clock
import java.time.LocalDate

class FakeClock(val localDate: LocalDate = LocalDate.of(2022, 4, 22)) : Clock {
    override fun now(): LocalDate = localDate
}