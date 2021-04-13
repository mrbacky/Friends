package com.rado.friends.ui.friends

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rado.friends.R
import com.rado.friends.data.Friend
import com.rado.friends.databinding.FragmentFriendsBinding
import com.rado.friends.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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


            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false;
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val friend = friendAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onFriendSwiped(friend)


                }


            }).attachToRecyclerView(rvFriends)

            fabAddFriend.setOnClickListener {
                viewModel.onAddNewFriendClick()

            }


        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)


        }




        viewModel.friends.observe(viewLifecycleOwner, friendAdapter::submitList)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.friendEvent.collect { event ->
                when (event) {
                    is FriendViewModel.FriendEvent.ShowUndoDeleteFriendMessage -> {
                        Snackbar.make(
                            requireView(),
                            "Friend deleted",
                            Snackbar.LENGTH_LONG
                        ).setAction("UNDO") {
                            viewModel.onUndoDeleteClick(event.friend)
                        }.show()
                    }
                    is FriendViewModel.FriendEvent.NavigateToAddFriendScreen -> {
                        val action =
                            FriendsFragmentDirections.actionFriendsFragmentToAddEditFriendFragment(
                                null,
                                "Add Friend"
                            )
                        findNavController().navigate(action)

                    }
                    is FriendViewModel.FriendEvent.NavigateToEditFriendScreen -> {
                        val action =
                            FriendsFragmentDirections.actionFriendsFragmentToAddEditFriendFragment(
                                event.friend, "Edit Friend"
                            )
                        findNavController().navigate(action)
                    }
                    is FriendViewModel.FriendEvent.ShowFriendSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }
                }.exhaustive

            }
        }

    }

    override fun onItemClick(friend: Friend) {
        viewModel.onFriendSelected(friend)
    }
}














