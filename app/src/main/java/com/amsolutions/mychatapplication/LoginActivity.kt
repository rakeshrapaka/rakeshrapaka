package com.amsolutions.mychatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    private lateinit var editUserName: EditText
    private lateinit var editPassword: EditText

    private lateinit var btnSubmit: Button
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editUserName = findViewById(R.id.usernameTxt)
        editPassword = findViewById(R.id.passwordTxt)
        btnSubmit = findViewById(R.id.submitBtn)
        btnSignUp = findViewById(R.id.signupBtn)

        btnSignUp.setOnClickListener{
            val intent = Intent(this,SingupActivity::class.java)
            startActivity(intent)
        }

    }
}