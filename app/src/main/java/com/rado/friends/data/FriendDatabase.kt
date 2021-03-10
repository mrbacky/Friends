package com.rado.friends.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Friend::class], version = 1)
abstract class FriendDatabase : RoomDatabase() {


    abstract fun friendDAO(): FriendDAO


    class Callback @Inject constructor(
        private val database: Provider<FriendDatabase>
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val friendDAO = database.get().friendDAO()
            // friendDAO.insert()
            // db stuff

        }
    }

}