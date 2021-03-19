package com.rado.friends.ui.addEditFriend

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rado.friends.data.Friend
import com.rado.friends.data.FriendDAO
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class AddEditFriendViewModel @AssistedInject constructor(
    private val friendDAO: FriendDAO,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {



    val friend = state.get<Friend>("friend")

    var friendName = state.get<String>("friendName") ?: friend?.name ?: ""
        set(value) {
            field = value
            state.set("friendName", value)
        }


}