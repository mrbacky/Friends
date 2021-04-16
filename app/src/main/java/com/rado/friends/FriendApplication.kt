package com.rado.friends

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Defining Application class and annotating with @HiltAndroidApp so Dagger Hilt can generate
 * necessary classes for our application.
 */
@HiltAndroidApp
class FriendApplication : Application() {}