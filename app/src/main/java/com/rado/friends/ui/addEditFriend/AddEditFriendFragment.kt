package com.rado.friends.ui.addEditFriend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rado.friends.R
import com.rado.friends.databinding.FragmentAddEditFriendBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditFriendFragment : Fragment(R.layout.fragment_add_edit_friend) {

    private val viewModel: AddEditFriendViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditFriendBinding.bind(view)
        binding.apply {
            etFriendName.setText(viewModel.friendName)

        }

    }

}