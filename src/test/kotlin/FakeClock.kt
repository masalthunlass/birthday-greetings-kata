import domain.Clock
import java.time.LocalDate

class FakeClock : Clock() {
    override fun now(): LocalDate = LocalDate.of(2022, 10, 8)
}