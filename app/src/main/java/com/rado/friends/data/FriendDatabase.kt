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

@Database(entities = [Friend::class], version = 2, exportSchema = false)
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
                friendDAO.insert(
                    Friend(
                        "Mike",
                        "Spangsbjerg Mollevej 87, Esbjerg, Denmark",
                        "81984784",
                        "mike@easv.dk",
                        "18/7/1990",
                        "www.mike.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Frank",
                        "Spangsbjerg Mollevej 123, Esbjerg, Denmark",
                        "25847484",
                        "frank@gmail.com",
                        "18/7/1990",
                        "www.mike.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Mia",
                        "Spangsbjerg Mollevej 43, Esbjerg, Denmark",
                        "25147484",
                        "mia@gmail.com",
                        "18/7/1990",
                        "www.mia.com"
                    )
                )


            }

            // friendDAO.insert()
            // db stuff

        }
    }

}