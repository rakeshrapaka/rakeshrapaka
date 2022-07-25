package com.amsolutions.mychatapplication.groups


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amsolutions.mychatapplication.R
import com.amsolutions.mychatapplication.chat.ChatActivity
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class GroupsActivity : AppCompatActivity() {

    private lateinit var vm: GroupViewModel
    private lateinit var adapter: GroupAdapter

    private lateinit var button_add_group: FloatingActionButton
    private lateinit var recycler_view: RecyclerView

    private lateinit var userId :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups_layout)

        userId = intent.getStringExtra("UserId")!!

        button_add_group = findViewById(R.id.button_create_grp)
        recycler_view = findViewById(R.id.recycler_view)

        setUpRecyclerView()

        setUpListeners()

        vm = ViewModelProviders.of(this)[GroupViewModel::class.java]

        vm.getAllGroups().observe(this, Observer {
            Log.i("Groups observed", "$it")
            var userList : MutableList<Groups> = mutableListOf()
            for(group in it){
                if(group.userID.equals(userId))
                userList.add(group)
            }
            adapter.submitList(userList)
        })

    }
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {  result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val name: String? = result.data!!.getStringExtra(EXTRA_NAME)
            name?.let { it1 -> Groups(it1, UUID.randomUUID().toString(), UUID.randomUUID().toString()) }?.let { it2 -> vm.insert(it2) }
            Toast.makeText(this, " Group Created", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setUpListeners() {
        button_add_group.setOnClickListener {

            val groupCreateIntent = Intent(this, AadEditGroupActivity::class.java)
            groupCreateIntent.putExtra("UserId",userId)
            startForResult.launch(groupCreateIntent)
        }

        // swipe listener
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val user = adapter.getGroupAt(viewHolder.adapterPosition)
                user.id?.let { vm.delete(it) }
            }

        }).attachToRecyclerView(recycler_view)
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = GroupAdapter { clickedNote ->
            val intent = Intent(this, AadEditGroupActivity::class.java)
            intent.putExtra(EXTRA_ID, clickedNote.id)
            intent.putExtra(EXTRA_NAME, clickedNote.groupName)
            var grpIntent = Intent(this, ChatActivity::class.java)
            grpIntent.putExtra("USER_ID", clickedNote.userID)
            grpIntent.putExtra("USER_NAME", "0")
            grpIntent.putExtra("GROUP_ID", clickedNote.groupId)
            grpIntent.putExtra("GROUP_NAME", clickedNote.groupName)
            //startForResult.launch(userIntent)
            startActivity(grpIntent)
        }
        recycler_view.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_user -> {
                vm.deleteAllUsers()
                Toast.makeText(this, "All users deleted!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}