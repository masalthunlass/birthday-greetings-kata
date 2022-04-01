package infrastructure

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import domain.Friend
import domain.FriendsSource


class FriendsFileSource : FriendsSource {

    override fun getAll(): Set<Friend> {
       return  csvReader().open("src/main/resources/friends.csv") {
             readAllWithHeaderAsSequence().map { row:Map<String, String> ->
                Friend(
                    row.getOrDefault("last_name", ""),
                    row.getOrDefault("first_name", ""))
            }.toSet()
        }
    }
}