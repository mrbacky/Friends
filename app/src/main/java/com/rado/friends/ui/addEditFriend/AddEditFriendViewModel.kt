package com.rado.friends.ui.addEditFriend

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.rado.friends.data.Friend
import com.rado.friends.data.FriendDAO
import com.rado.friends.ui.ADD_FRIEND_RESULT_OK
import com.rado.friends.ui.EDIT_TASK_RESULT_OK
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

    var friendAddress = state.get<String>("friendAddress") ?: friend?.address ?: ""
        set(value) {
            field = value
            state.set("friendAddress", value)
        }

    var friendPhone = state.get<String>("friendPhone") ?: friend?.phone ?: ""
        set(value) {
            field = value
            state.set("friendPhone", value)
        }

    var friendEmail = state.get<String>("friendEmail") ?: friend?.email ?: ""
        set(value) {
            field = value
            state.set("friendEmail", value)
        }

    var friendBirthday = state.get<String>("friendBirthday") ?: friend?.birthday ?: ""
        set(value) {
            field = value
            state.set("friendBirthday", value)
        }

    var friendWebsite = state.get<String>("friendWebsite") ?: friend?.website ?: ""
        set(value) {
            field = value
            state.set("friendWebsite", value)
        }


    private val addEditFriendEventChannel = Channel<AddEditFriendEvent>()
    val addEditFriendEvent = addEditFriendEventChannel.receiveAsFlow()

    fun onSaveClick() {

        if (friendName.isBlank()) {
            showInvalidInputMessage("Name cannot be empty")
            return
        }
        if (friendAddress.isBlank()) {
            showInvalidInputMessage("Address cannot be empty")
            return
        }
        if (friendBirthday.isBlank()) {
            showInvalidInputMessage("Birthday cannot be empty")
            return
        }
        if (friendPhone.isBlank()) {
            showInvalidInputMessage("Phone cannot be empty")
            return
        }
        if (friendEmail.isBlank()) {
            showInvalidInputMessage("Email cannot be empty")
            return
        }
        if (friendWebsite.isBlank()) {
            showInvalidInputMessage("Website cannot be empty")
            return
        }

        if (friend != null) {
            val updatedFriend = friend.copy(
                name = friendName,
                address = friendAddress,
                phone = friendPhone,
                email = friendEmail,
                birthday = friendBirthday,
                website = friendWebsite
            )
            updateFriend(updatedFriend)

        } else {
            val newFriend = Friend(
                name = friendName,
                address = friendAddress,
                phone = friendPhone,
                email = friendEmail,
                birthday = friendBirthday,
                website = friendWebsite
            )
            createFriend(newFriend)
        }
    }

    private fun showInvalidInputMessage(s: String) = viewModelScope.launch {
        addEditFriendEventChannel.send(AddEditFriendEvent.ShowInvalidInputMessage(s))
    }

    private fun createFriend(newFriend: Friend) = viewModelScope.launch {
        friendDAO.insert(newFriend)
        addEditFriendEventChannel.send(
            AddEditFriendEvent.NavigateBackWithResult(
                ADD_FRIEND_RESULT_OK
            )
        )
    }

    private fun updateFriend(updatedFriend: Friend) = viewModelScope.launch {
        friendDAO.update(updatedFriend)
        addEditFriendEventChannel.send(AddEditFriendEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))

    }

    /**
     * Exposing AddEditFriendEvent sealed class for FriendsFragment consumption.
     * Fragment should be responsible as a view to display snackbar,
     * therefore the FriendsFragment consumes events from FriendEventChannel.
     */
    sealed class AddEditFriendEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditFriendEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditFriendEvent()
    }
}