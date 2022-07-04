package com.example.medicinelist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserCreation : AppCompatActivity() {
    val fb = Firebase
    val fbAuth: FirebaseAuth = fb.auth

    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var btn_login: Button
    lateinit var btn_register: Button

    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_creation)
        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_password)
        btn_login = findViewById(R.id.btn_login)
        btn_register = findViewById(R.id.btn_register)

        btn_register.setOnClickListener {
            email = edt_email.text.toString()
            password = edt_password.text.toString()

            fbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful)
                {
                    val fbUser: FirebaseUser = it.result.user!!
                    Toast.makeText(this,"Task Successful",Toast.LENGTH_SHORT).show()
                    val i = Intent(this@UserCreation,MainScreen::class.java)
                    i.putExtra("email",email)
                    i.putExtra("uid",fbUser.uid)
                    startActivity(i)

                }
                else
                {
                    Toast.makeText(this,"Task Failed ",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}