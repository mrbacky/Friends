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

    /**
     * Annotating Callback class with @Inject so Dagger Hilt can inject the Callback instance to Database.
     *
     * Callback needs Database and Database needs Callback. There is circular dependency.
     * onCreate method in Callback will be executed after building the database.
     * We have to define database provider so we can avoid circular dependency.
     * After the database is built, Callback instance gets the database via db provider,
     * get DAO and execute insertion of initial data
     *
     *
     *
     */
    class Callback @Inject constructor(
        private val database: Provider<FriendDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)


            val friendDAO = database.get().friendDAO()
            //  running DAO functions in coroutineScope that is constructed by Dagger Hilt
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
        }
    }

}