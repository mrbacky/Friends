package com.rado.friends.ui.addEditFriend

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
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

            etFriendAddress.setText(viewModel.friendAddress)
            etFriendAddress.addTextChangedListener {
                viewModel.friendAddress = it.toString()
            }

            etFriendPhone.setText(viewModel.friendPhone)
            etFriendPhone.addTextChangedListener {
                viewModel.friendPhone = it.toString()
            }

            etFriendEmail.setText(viewModel.friendEmail)
            etFriendEmail.addTextChangedListener {
                viewModel.friendEmail = it.toString()
            }

            etFriendBirthday.setText(viewModel.friendBirthday)
            etFriendBirthday.addTextChangedListener {
                viewModel.friendBirthday = it.toString()
            }

            etFriendWebsite.setText(viewModel.friendWebsite)
            etFriendWebsite.addTextChangedListener {
                viewModel.friendWebsite = it.toString()
            }

            fabSaveFriend.setOnClickListener {
                viewModel.onSaveClick()

            }

            ibPhone.setOnClickListener {
                val phoneNumber = etFriendPhone.text
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                startActivity(intent)
            }

            ibMessage.setOnClickListener {
                val phoneNumber = etFriendPhone.text
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:" + Uri.encode(phoneNumber.toString()))
                startActivity(intent)

            }

            ibEmail.setOnClickListener {
                val email = etFriendEmail.text
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data =
                    Uri.parse("mailto:" + Uri.encode(email.toString()))
                startActivity(intent)
            }

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

}

