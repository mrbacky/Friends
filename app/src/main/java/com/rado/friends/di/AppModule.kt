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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: FriendDatabase.Callback
    ) = databaseBuilder(app, FriendDatabase::class.java, "friend_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideFriendDAO(db: FriendDatabase) = db.friendDAO()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope