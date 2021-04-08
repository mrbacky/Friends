package com.rado.friends.ui.addEditFriend

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rado.friends.data.Friend
import com.rado.friends.data.FriendDAO
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFriendViewModel @Inject constructor(
    private val friendDAO: FriendDAO,
    private val state: SavedStateHandle
) : ViewModel() {


    val friend = state.get<Friend>("friend")

    var friendName = state.get<String>("friendName") ?: friend?.name ?: ""
        set(value) {
            field = value
            state.set("friendName", value)
        }


    private val addEditFriendEventChannel = Channel<AddEditFriendEvent>()
    val addEditFriendEvent = addEditFriendEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (friendName.isBlank()) {
            showInvalidInputMessage("Name cannot be empty")

        }

        if (friend != null) {
            val updatedFriend = friend.copy(name = friendName)
            updateFriend(updatedFriend)

        } else {
            val newFriend = Friend(name = friendName)
            createFriend(newFriend)
        }
    }

    private fun showInvalidInputMessage(s: String) = viewModelScope.launch {
        addEditFriendEventChannel.send(AddEditFriendEvent.ShowInvalidInputMessage(s))
    }

    private fun createFriend(newFriend: Friend) = viewModelScope.launch {
        friendDAO.insert(newFriend)
        //addEditFriendEventChannel.send(AddEditFriendEvent.NavigateBackWithResult())
    }

    private fun updateFriend(updatedFriend: Friend) = viewModelScope.launch {
        friendDAO.update(updatedFriend)
        //addEditFriendEventChannel.send(AddEditFriendEvent.NavigateBackWithResult())
    }


    sealed class AddEditFriendEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditFriendEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditFriendEvent()
    }
}