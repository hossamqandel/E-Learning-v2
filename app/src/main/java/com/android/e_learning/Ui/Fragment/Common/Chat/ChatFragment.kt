package com.android.e_learning.Ui.Fragment.Common.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.e_learning.Adapter.ChatAdapter
import com.android.e_learning.R
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    var currentCourseId: String? = null
    val viewModel: ChatViewModel by viewModels()
    val mAdapter = ChatAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        currentCourseId = ChatFragmentArgs.fromBundle(requireArguments()).currentCourseId
//        viewModel.fetchChatMessages(currentCourseId)
        viewModel.grabChatMessages(currentCourseId)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        viewModel.mToastMessage.observe(viewLifecycleOwner){ toastShort(context, it) }
        viewModel.mProgressBar.observe(viewLifecycleOwner){ binding.progressBar.visibility = it }
        viewModel.mChatMutable.observe(viewLifecycleOwner){
            mAdapter.setChatList(it)
            binding.recyclerChat.adapter = mAdapter
        }

    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            chatSendMessageBTN.setOnClickListener {
                var textMessage = chatMessageET.text.toString().trim()
                if (textMessage.isNotEmpty() && currentCourseId!!.isNotEmpty()) {
                    viewModel.sendMessage(textMessage, currentCourseId)
                    binding.chatMessageET.text.clear()
                }
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}