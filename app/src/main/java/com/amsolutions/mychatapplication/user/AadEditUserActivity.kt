package com.amsolutions.mychatapplication.user

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.amsolutions.mychatapplication.R

 const val EXTRA_NAME : String = "USERNAME"
 const val EXTRA_NUM : String = "MOBILENUM"
 const val EXTRA_ID: String = "USERID"

class AadEditUserActivity : AppCompatActivity() {

    private lateinit var mode : Mode

    private var userId: Int = -1

    private lateinit var et_name:EditText
    private lateinit var et_mail:EditText
    private lateinit var btn_save:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aad_edit_user)

        et_name = findViewById(R.id.et_uname)
        et_mail = findViewById(R.id.et_mail)

        userId = intent.getIntExtra(EXTRA_ID, -1)
        mode = if(userId == -1) Mode.AddUser
        else Mode.EditUser

        when(mode) {
            Mode.AddUser -> title = "Add User"
            Mode.EditUser -> {
                title = "Edit User"
                et_name.setText(intent.getStringExtra(EXTRA_NAME))
                et_mail.setText(intent.getStringExtra(EXTRA_NUM))
            }
        }

        btn_save = findViewById(R.id.saveBtn)
        btn_save.setOnClickListener{
            saveUser()
        }

    }

    private fun saveUser() {
        val name = et_name.text.toString()
        val userMail = et_mail.text.toString()

        if(name.isEmpty() || userMail.isEmpty()) {
            Toast.makeText(this, "please insert name and mobile number", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        // only if note ID was provided i.e. we are editing
        if(userId != -1)
            data.putExtra(EXTRA_ID, userId)
        data.putExtra(EXTRA_NAME, name)
        data.putExtra(EXTRA_NUM, userMail)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private sealed class Mode {
        object AddUser : Mode()
        object EditUser : Mode()
    }
}