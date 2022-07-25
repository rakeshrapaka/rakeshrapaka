package com.amsolutions.mychatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.amsolutions.mychatapplication.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SingupActivity : AppCompatActivity() {

    private val TAG: String = "Create User"
    private lateinit var editUserName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText

    private lateinit var btnSignUp: Button

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singuo)

        mAuth = FirebaseAuth.getInstance()

        editUserName = findViewById(R.id.usernameTxt)
        editPassword = findViewById(R.id.passwordTxt)
        editEmail = findViewById(R.id.emailTxt)
        btnSignUp = findViewById(R.id.signupBtn)

        btnSignUp.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val name = editUserName.text.toString()
            signUp(name,email,password)
        }

    }

    private fun signUp(name:String?,email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    addUser(name,email,user?.uid!!)
                    var mainIntent = Intent(this,MainActivity::class.java)
                    mainIntent.putExtra("UserID",user?.uid!!)
                    startActivity(mainIntent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUser(name:String?,email: String?,uid:String?){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        if (uid != null) {
            mDbRef.child("user").child(uid).setValue(User(name,email,uid))
        }
    }
}