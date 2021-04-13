package com.rado.friends.ui.addEditFriend

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rado.friends.R
import com.rado.friends.databinding.FragmentAddEditFriendBinding
import com.rado.friends.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditFriendFragment : Fragment(R.layout.fragment_add_edit_friend) {

    private val viewModel: AddEditFriendViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditFriendBinding.bind(view)
        binding.apply {
            etFriendName.setText(viewModel.friendName)

            etFriendName.addTextChangedListener {
                viewModel.friendName = it.toString()

            }

            fabSaveFriend.setOnClickListener {
                viewModel.onSaveClick()

            }


        }

//        fun hideKeyboardFrom(context: Context, view: View) {
//            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
//        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditFriendEvent.collect { event ->
                when (event) {
                    is AddEditFriendViewModel.AddEditFriendEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }

                    is AddEditFriendViewModel.AddEditFriendEvent.NavigateBackWithResult -> {
                        binding.etFriendName.clearFocus()
                        setFragmentResult(
                            requestKey = "add_edit_request",
                            bundleOf("add_edit_result" to event.result)


                        )
                        findNavController().popBackStack()
                    }

                }.exhaustive
            }
        }


    }

}

