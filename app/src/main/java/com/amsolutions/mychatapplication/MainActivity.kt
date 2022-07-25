package com.amsolutions.mychatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.amsolutions.mychatapplication.groups.GroupsActivity
import com.amsolutions.mychatapplication.user.UserActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var userButton: Button
    private lateinit var groupsButton: Button

    private  var userId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent?.getStringExtra("UserID")!!

        mAuth = FirebaseAuth.getInstance()

        userButton = findViewById(R.id.userBtn)
        userButton.setOnClickListener{
            val userIntent = Intent(this,UserActivity::class.java)
            userIntent.putExtra("UserId",userId)
            startActivity(userIntent)
        }

        groupsButton = findViewById(R.id.grpsBtn)
        groupsButton.setOnClickListener{
            val groupIntent = Intent(this,GroupsActivity::class.java)
            groupIntent.putExtra("UserId",userId)
            startActivity(groupIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.signout_user){
            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return true
    }
}