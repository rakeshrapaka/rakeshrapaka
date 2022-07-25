package com.amsolutions.mychatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var editUserName: EditText
    private lateinit var editPassword: EditText

    private lateinit var btnSubmit: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        editUserName = findViewById(R.id.usernameTxt)
        editPassword = findViewById(R.id.passwordTxt)
        btnSubmit = findViewById(R.id.submitBtn)
        btnSignUp = findViewById(R.id.signupBtn)

        btnSignUp.setOnClickListener{
            val intent = Intent(this,SingupActivity::class.java)
            startActivity(intent)
        }

        btnSubmit.setOnClickListener{
            val email = editUserName.text.toString()
            val password = editPassword.text.toString()

            login(email,password)
        }

    }

    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            task ->
            if(task.isSuccessful){
                val user = mAuth.currentUser?.uid
                var mainIntent = Intent(this,MainActivity::class.java)
                mainIntent.putExtra("UserID",user)
                startActivity(mainIntent)
                finish()
            }else{
                Toast.makeText(this,"User Does not exist ",Toast.LENGTH_SHORT).show()
            }
        }
    }
}