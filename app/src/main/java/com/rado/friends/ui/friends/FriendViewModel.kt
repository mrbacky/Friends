package com.rado.friends.ui.friends

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rado.friends.data.Friend
import com.rado.friends.data.FriendDAO
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendDao: FriendDAO,
) : ViewModel() {


    val friends = friendDao.getFriends().asLiveData()


    sealed class FriendEvent {

        object NavigateToAddFriendScreen : FriendEvent()
        data class NavigateToEditFriendScreen(val friend: Friend) : FriendEvent()

    }


    fun onFriendSelected(friend: Friend) {

    }

    fun onAddNewFriendClick() = viewModelScope.launch {
    }
}