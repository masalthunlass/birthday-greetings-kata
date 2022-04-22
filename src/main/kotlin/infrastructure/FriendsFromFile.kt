package infrastructure

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import domain.Friend
import domain.FriendRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FriendsFromFile(private val filePath: String) : FriendRepository {

    private val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    override fun getAll(): Set<Friend> {
        return csvReader().open(filePath) {
            readAllWithHeaderAsSequence().map { row: Map<String, String> ->
                Friend(
                    formatDate(row.getOrDefault("date_of_birth", "")),
                    row.getOrDefault("first_name", ""),
                    row.getOrDefault("email", ""),
                )
            }.toSet()
        }
    }

    private fun formatDate(date: String): LocalDate = LocalDate.parse(date, formatter)

}