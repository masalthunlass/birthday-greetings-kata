package domain

interface FriendRepository {
    fun getAll(): Set<Friend>
}