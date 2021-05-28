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

@Database(entities = [Friend::class], version = 1, exportSchema = false)
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
                        "Mike Tyson",
                        "Spangsbjerg Mollevej 87, Esbjerg, Denmark",
                        "81984784",
                        "mike@easv.dk",
                        "18/7/1990",
                        "www.mike.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Jim Fox",
                        "Spangsbjerg Mollevej 333, Esbjerg, Denmark",
                        "81984784",
                        "mike@easv.dk",
                        "18/7/1990",
                        "www.jim.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Frank Johnson",
                        "Spangsbjerg Mollevej 123, Esbjerg, Denmark",
                        "45786756",
                        "frank@gmail.com",
                        "18/7/1990",
                        "www.frank.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Mia Smith",
                        "Spangsbjerg Mollevej 43, Esbjerg, Denmark",
                        "23457654",
                        "mia@gmail.com",
                        "18/7/1990",
                        "www.mia.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Travis Scott",
                        "Spangsbjerg Mollevej 665, Esbjerg, Denmark",
                        "25147484",
                        "mia@gmail.com",
                        "10/3/1994",
                        "www.travis.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Henry Thomson",
                        "Spangsbjerg Mollevej 567, Esbjerg, Denmark",
                        "54225856",
                        "mia@gmail.com",
                        "3/7/1999",
                        "www.henry.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Gus Fring",
                        "Spangsbjerg Mollevej 3698, Esbjerg, Denmark",
                        "81849196",
                        "gus@gmail.com",
                        "3/7/1980",
                        "www.gus.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Sara",
                        "Spangsbjerg Mollevej 577, Esbjerg, Denmark",
                        "81874196",
                        "sara@gmail.com",
                        "3/7/1980",
                        "www.sara.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Walter White",
                        "Spangsbjerg Mollevej 83, Esbjerg, Denmark",
                        "81447896",
                        "walt@gmail.com",
                        "3/7/1981",
                        "www.walt.com"
                    )
                )
                friendDAO.insert(
                    Friend(
                        "Hank",
                        "Spangsbjerg Mollevej 98, Esbjerg, Denmark",
                        "81863896",
                        "Hank@gmail.com",
                        "3/7/1981",
                        "www.hank.com"
                    )
                )
            }
        }
    }

}