package com.amsolutions.mychatapplication.groups

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amsolutions.mychatapplication.R
import com.amsolutions.mychatapplication.chat.ChatActivity
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.data.repository.GroupsRepository
import com.amsolutions.mychatapplication.data.repository.UserRepository
import com.amsolutions.mychatapplication.user.UserAdapter
import com.amsolutions.mychatapplication.user.UserSelectionAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

const val EXTRA_NAME : String = "USERNAME"
 const val EXTRA_NUM : String = "MOBILENUM"
 const val EXTRA_ID: String = "USERID"

class AadEditGroupActivity : AppCompatActivity() {

    private lateinit var mode : Mode

    private var extraId: Int = -1

    private lateinit var userSelectionRecyclerView: RecyclerView
    private lateinit var adapter: UserSelectionAdapter

    private lateinit var et_name:EditText

    private lateinit var btn_save:Button

    private lateinit var vm: GroupViewModel

    private lateinit var userId:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_group)
        userId = intent.getStringExtra("UserId")!!
        vm = ViewModelProviders.of(this)[GroupViewModel::class.java]
        et_name = findViewById(R.id.et_groupname)

        userSelectionRecyclerView = findViewById(R.id.user_selection_recycler_view)
        setUpRecyclerView()

        vm.getAllUsers().observe(this, androidx.lifecycle.Observer {
            Log.i("Groups observed", "$it")

            adapter.submitList(it)
        })

        extraId = intent.getIntExtra(EXTRA_ID, -1)
        mode = if(extraId == -1) Mode.AddUser
        else Mode.EditUser

        when(mode) {
            Mode.AddUser -> title = "Add User"
            Mode.EditUser -> {
                title = "Edit User"
                et_name.setText(intent.getStringExtra(EXTRA_NAME))
            }
        }

        btn_save = findViewById(R.id.saveBtn)
        btn_save.setOnClickListener{
            createGroup()
        }

    }

    private fun setUpRecyclerView() {
        userSelectionRecyclerView.layoutManager = LinearLayoutManager(this)
        userSelectionRecyclerView.setHasFixedSize(true)
        adapter = UserSelectionAdapter()
        userSelectionRecyclerView.adapter = adapter
    }


    private fun createGroup() {
        val name = et_name.text.toString()

        if(name.isEmpty()) {
            Toast.makeText(this, "please insert name and mobile number", Toast.LENGTH_SHORT).show()
            return
        }

        val itemCount = userSelectionRecyclerView.adapter?.itemCount
        for (i in 0 until itemCount!!) {
            val holder = userSelectionRecyclerView.findViewHolderForAdapterPosition(i)
            if (holder != null) {
                val userSelected = holder.itemView.findViewById<View>(R.id.selectedUser) as CheckBox
                if(userSelected.visibility == View.VISIBLE){
                    val tempUser = adapter.getUserAt(i)
                    userId?.let { Groups(name, it,UUID.randomUUID().toString()) }
                        ?.let { vm.insert(it) }
                }
            }
        }

        val data = Intent()
        // only if note ID was provided i.e. we are editing
        if(extraId != -1)
            data.putExtra(EXTRA_ID, userId)
        data.putExtra(EXTRA_NAME, name)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private sealed class Mode {
        object AddUser : Mode()
        object EditUser : Mode()
    }
}