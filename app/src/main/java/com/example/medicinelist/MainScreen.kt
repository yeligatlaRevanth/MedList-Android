package com.example.medicinelist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainScreen : AppCompatActivity() {
    //Widget Variables
    lateinit var edtV1_uName: EditText
    lateinit var edtV1_uAge: EditText
    lateinit var btn1_Register: Button
    lateinit var radioGroup: RadioGroup

    //Data Variables for Widgets
    lateinit var u1Name: String
    lateinit var u1Age: String
    var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        //Initializing Widgets
        edtV1_uName = findViewById(R.id.edtV1_uName)
        edtV1_uAge = findViewById(R.id.edtV1_uAge)
        btn1_Register = findViewById(R.id.btn1_Register)
        radioGroup = findViewById(R.id.radioGroup)

        radioButtonWorking()
        btn1_Register.setOnClickListener {
            onButtonClick()
        }
    }
    fun radioButtonWorking()
    {
        //Called when a radiobutton inside radiogroup is checked/changed
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton: RadioButton = findViewById(checkedId)    //Finding which radiobutton is checked by passing checkedID as parameter
            if(checkedRadioButton != null)
            {
                gender = checkedRadioButton.text.toString()
            }
        }
    }
    fun onButtonClick()
    {
        u1Name = edtV1_uName.text.toString()
        u1Age = edtV1_uAge.text.toString()
        if(u1Name == "" || u1Age =="" || gender =="")
        {
            Toast.makeText(this@MainScreen,"Form Incomplete",Toast.LENGTH_SHORT).show()
        }
        else
        {
            val i = Intent(this@MainScreen, UserProfile::class.java)
            i.putExtra("uName", u1Name)
            i.putExtra("uAge", u1Age)
            i.putExtra("uGender",gender)
            startActivity(i)
        }
    }
}