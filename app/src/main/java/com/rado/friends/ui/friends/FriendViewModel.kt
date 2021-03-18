package com.rado.friends.ui.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rado.friends.data.FriendDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendDao: FriendDAO
) : ViewModel() {


    val friends = friendDao.getFriends().asLiveData()


}