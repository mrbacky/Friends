package com.rado.friends.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * Extension property.
 * This way 'when' statement in Fragments requires implementing all possible Events,
 * code is compile time safe.
 */
val <T> T.exhaustive: T
    get() = this

