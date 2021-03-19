package com.rado.friends.ui.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rado.friends.R
import com.rado.friends.data.Friend
import com.rado.friends.databinding.FragmentFriendsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendsFragment : Fragment(R.layout.fragment_friends), FriendAdapter.OnItemClickListener {

    private val viewModel: FriendViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFriendsBinding.bind(view)

        val friendAdapter = FriendAdapter(this)

        binding.apply {
            rvFriends.apply {
                adapter = friendAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)

            }

            fabAddFriend.setOnClickListener {
                viewModel.onAddNewFriendClick()
            }
        }

        viewModel.friends.observe(viewLifecycleOwner, friendAdapter::submitList)


    }

    override fun onItemClick(friend: Friend) {
        viewModel.onFriendSelected(friend)
    }
}