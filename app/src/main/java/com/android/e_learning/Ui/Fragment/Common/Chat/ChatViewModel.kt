package com.android.e_learning.Ui.Fragment.Common.Chat

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.ChatModel
import com.android.e_learning.Utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    //TODO("progressBar always loading if there is no data")
    //TODO("get the real user name from firebase instead of using 'Hossam' as a temporary name")

    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    var userName: String? = null
    val mProgressBar = MutableLiveData<Int>()
    val mToastMessage = MutableLiveData<String?>()
    val mChatMutable = MutableLiveData<ArrayList<ChatModel>>()
    private var listOfChatMessages = ArrayList<ChatModel>()

    fun sendMessage(message: String?, courseId: String?) {
        viewModelScope.launch {
                Const.mDatabaseref.child(Const.CHATS).child(courseId!!).push()
                    .setValue(ChatModel(message, "Hossam", Const.currentUserId))
            }
        }


    fun grabChatMessages(courseId: String?){
        listOfChatMessages.clear()
        viewModelScope.launch(Dispatchers.IO){
            mProgressBar.postValue(View.VISIBLE)

            Const.mDatabaseref.child(Const.CHATS).child(courseId!!).addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mProgressBar.postValue(View.GONE)
                    var chatModel = snapshot.getValue(ChatModel::class.java)
                    listOfChatMessages.add(chatModel!!)
                    mChatMutable.postValue(listOfChatMessages)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    mProgressBar.postValue(View.GONE)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    mProgressBar.postValue(View.GONE)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    mProgressBar.postValue(View.GONE)
                }

                override fun onCancelled(error: DatabaseError) {
                    mToastMessage.postValue(error.message)
                    mProgressBar.postValue(View.GONE)
                }

            })
        }
        mProgressBar.postValue(View.GONE)
    }




}