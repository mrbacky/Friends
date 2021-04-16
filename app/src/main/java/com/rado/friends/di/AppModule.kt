package com.rado.friends.di

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.rado.friends.data.FriendDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * AppModule is a container for dependencies.
 * With functions we can define how Dagger Hilt
 * can create dependencies and Dagger Hilt will inject them when needed.
 */
@Module
//  Dagger Hilt is holding AppModule for dependencies in SingletonComponent
//  because we need dependencies for whole application
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: FriendDatabase.Callback
    ) = databaseBuilder(app, FriendDatabase::class.java, "friend_database")
        .fallbackToDestructiveMigration()
        //  Callback is executed after build()
        .addCallback(callback)
        .build()

    @Provides
    fun provideFriendDAO(db: FriendDatabase) = db.friendDAO()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}


/**
 *  Custom ApplicationScope for running suspend functions
 *  in coroutines.We may have multiple scopes, therefore
 *  it is good practice to define type of scope in AppModule
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope