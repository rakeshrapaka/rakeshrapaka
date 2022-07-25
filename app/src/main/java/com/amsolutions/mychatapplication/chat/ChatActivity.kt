package com.amsolutions.mychatapplication.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amsolutions.mychatapplication.R
import com.amsolutions.mychatapplication.data.model.Message


class ChatActivity : AppCompatActivity() {

    private lateinit var vm: MessageViewModel
    private lateinit var adapter: MessageAdapter

    private lateinit var recycler_view: RecyclerView

    private lateinit var nameTextView: TextView
    private lateinit var msgEditText: EditText
    private lateinit var sendImg : ImageView

    private lateinit var userId : String
    private lateinit var userName: String
    private lateinit var groupId : String
    private lateinit var groupName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        userId = intent.getStringExtra("USER_ID")!!
        userName = intent.getStringExtra("USER_NAME")!!
        groupId = intent.getStringExtra("GROUP_ID")!!
        groupName = intent.getStringExtra("GROUP_NAME")!!

        recycler_view = findViewById(R.id.chatRecyclerView)

        setUpRecyclerView()

        nameTextView = findViewById(R.id.userNameDisplayTxt)

        if(groupId.equals("0")){
            nameTextView.text = userName
        }else{
            nameTextView.text = groupName
        }

        msgEditText = findViewById(R.id.userChatTxt)

        sendImg = findViewById(R.id.sendBtn)

        vm = ViewModelProviders.of(this)[MessageViewModel::class.java]

        vm.getAllMessages().observe(this, Observer {
            Log.i("Message observed", "$it")

            adapter.submitList(it)
        })

        sendImg.setOnClickListener{
            val userMsg = msgEditText.text.toString()
            if(userMsg.isNotBlank()){
                vm.insert(Message(userMsg,userName,userId,groupId))
                msgEditText.setText("")
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = MessageAdapter(userId)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

}