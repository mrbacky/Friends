package com.rado.friends.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rado.friends.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Friend::class], version = 1)
abstract class FriendDatabase : RoomDatabase() {


    abstract fun friendDAO(): FriendDAO


    class Callback @Inject constructor(
        private val database: Provider<FriendDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val friendDAO = database.get().friendDAO()
            applicationScope.launch {
                friendDAO.insert(Friend("Mike"))
                friendDAO.insert(Friend("Frank"))
                friendDAO.insert(Friend("Jim"))
                friendDAO.insert(Friend("Mia"))
                friendDAO.insert(Friend("Joe"))
                friendDAO.insert(Friend("Wiz"))
                friendDAO.insert(Friend("Walt"))
                friendDAO.insert(Friend("Gus"))
                friendDAO.insert(Friend("Freddy"))
                friendDAO.insert(Friend("Nicolas"))
                friendDAO.insert(Friend("Hans"))
                friendDAO.insert(Friend("Tim"))
                friendDAO.insert(Friend("Harry"))
                friendDAO.insert(Friend("Ron"))

            }

            // friendDAO.insert()
            // db stuff

        }
    }

}