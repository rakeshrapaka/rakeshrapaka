package com.amsolutions.mychatapplication.user


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
import com.amsolutions.mychatapplication.data.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


const val ADD_NOTE_REQUEST = 1
const val EDIT_NOTE_REQUEST = 2

class UserActivity : AppCompatActivity() {

    private lateinit var vm: UserViewModel
    private lateinit var adapter: UserAdapter

    private lateinit var button_add_user: FloatingActionButton
    private lateinit var recycler_view: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_layout)

        button_add_user = findViewById(R.id.button_add_user)
        recycler_view = findViewById(R.id.recycler_view)

        setUpRecyclerView()

        setUpListeners()

        vm = ViewModelProviders.of(this)[UserViewModel::class.java]

        vm.getAllUsers().observe(this, Observer {
            Log.i("User observed", "$it")

            adapter.submitList(it)
        })

    }

    val startForNewResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {  result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val name: String? = result.data!!.getStringExtra(EXTRA_NAME)
            val email: String? =
                result.data!!.getStringExtra(EXTRA_NUM)

            name?.let { it1 -> User(it1, email, UUID.randomUUID().toString()) }?.let { it2 -> vm.insert(it2) }
            Toast.makeText(this, "User added!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpListeners() {
        button_add_user.setOnClickListener {

            val intent = Intent(this, AadEditUserActivity::class.java)
            startForNewResult.launch(intent)
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
                val user = adapter.getUserAt(viewHolder.adapterPosition)
                user.id?.let { vm.delete(it) }
            }

        }).attachToRecyclerView(recycler_view)
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = UserAdapter { clickedNote ->
            var userIntent = Intent(this, ChatActivity::class.java)
            userIntent.putExtra("USER_ID", clickedNote.uid)
            userIntent.putExtra("USER_NAME", clickedNote.name)
            userIntent.putExtra("GROUP_ID", "0")
            userIntent.putExtra("GROUP_NAME", "0")
            //startForResult.launch(userIntent)
            startActivity(userIntent)
        }
        recycler_view.adapter = adapter
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {  result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val id = result.data!!.getIntExtra(EXTRA_ID, -1)
            if(id == -1) {
                Toast.makeText(this, "User couldn't be updated!", Toast.LENGTH_SHORT).show()
            }else {
                val uName: String? = result.data!!.getStringExtra(EXTRA_NAME)
                val email: String? =
                    result.data!!.getStringExtra(EXTRA_NUM)

                uName?.let { User(it, email, UUID.randomUUID().toString()) }
                    ?.let { vm.update(it) }
                Toast.makeText(this, "User updated!", Toast.LENGTH_SHORT).show()
            }

        }
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