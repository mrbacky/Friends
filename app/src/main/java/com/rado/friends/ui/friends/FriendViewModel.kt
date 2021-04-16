package com.rado.friends.ui.friends

import android.util.EventLog
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
class FriendViewModel @Inject constructor(
    private val friendDao: FriendDAO,
) : ViewModel() {

    val friends = friendDao.getFriends().asLiveData()

    private val friendEventChannel = Channel<FriendEvent>()
    //  Exposing event channel as a flow to restrict fragment to do only consume events.
    val friendEvent = friendEventChannel.receiveAsFlow()


    fun onFriendSelected(friend: Friend) = viewModelScope.launch {
        friendEventChannel.send(FriendEvent.NavigateToEditFriendScreen(friend))
    }

    fun onAddNewFriendClick() = viewModelScope.launch {
        friendEventChannel.send(FriendEvent.NavigateToAddFriendScreen)

    }

    fun onFriendSwiped(friend: Friend) = viewModelScope.launch {
        friendDao.delete(friend)
        friendEventChannel.send(FriendEvent.ShowUndoDeleteFriendMessage(friend))
    }

    fun onUndoDeleteClick(friend: Friend) = viewModelScope.launch {
        friendDao.insert(friend)
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_FRIEND_RESULT_OK -> showFriendSavedConfirmationMessage("Friend added")
            EDIT_TASK_RESULT_OK -> showFriendSavedConfirmationMessage("Friend updated")
        }
    }

    private fun showFriendSavedConfirmationMessage(s: String) = viewModelScope.launch {
        friendEventChannel.send(FriendEvent.ShowFriendSavedConfirmationMessage(s))

    }


    sealed class FriendEvent {

        data class ShowUndoDeleteFriendMessage(val friend: Friend) : FriendEvent()
        object NavigateToAddFriendScreen : FriendEvent()
        data class NavigateToEditFriendScreen(val friend: Friend) : FriendEvent()
        data class ShowFriendSavedConfirmationMessage(val msg: String) : FriendEvent()

    }


}