package com.rado.friends.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDAO {

    @Query("SELECT * FROM friends")
    fun getFriends(): Flow<List<Friend>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friend: Friend)

    @Update
    suspend fun update(friend: Friend)

    @Delete
    suspend fun delete(friend: Friend)

}