package com.example.medicinelist

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import java.util.*

class UserProfile : AppCompatActivity() {

    //Widget Variables
    lateinit var txt2_uName: TextView
    lateinit var txt2_uAge: TextView
    lateinit var txt2_uGender: TextView
    lateinit var edt2_MName: EditText
    lateinit var edt2_MTime: EditText
    lateinit var btn2_addMedicine: Button
    lateinit var txt2_VMList: TextView

    //Data variables for Widgets
    lateinit var u2_Name: String
    lateinit var u2_Age: String
    lateinit var u2_Gender: String
    lateinit var u2_MedName: String

    //Firebase
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val refMed: DatabaseReference = database.reference.child("Med")

    //Calendar variables
    val myCalendar = Calendar.getInstance()
    var sHour: Int = -1
    var sMin: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //Initializing Widgets
        txt2_uName = findViewById(R.id.txt2_uName)
        txt2_uAge = findViewById(R.id.txt2_uAge)
        txt2_uGender = findViewById(R.id.txt2_uGender)
        edt2_MName = findViewById(R.id.edt2_MName)
        edt2_MTime = findViewById(R.id.edt2_MTime)
        btn2_addMedicine = findViewById(R.id.btn2_addMedicine)
        txt2_VMList = findViewById(R.id.txt2_VMList)

        //Receiving Data from MainScreen
        u2_Name = intent.getStringExtra("uName").toString()
        u2_Age = intent.getStringExtra("uAge").toString()
        u2_Gender = intent.getStringExtra("uGender").toString()

        //Setting data to Widgets
        txt2_uName.setText(u2_Name)
        txt2_uAge.setText(u2_Age)
        txt2_uGender.setText(u2_Gender)

        //Setting Time
        edt2_MTime.setOnClickListener {
            getTime()
        }

        //Button or Text Click
        btn2_addMedicine.setOnClickListener {
            onButtonClick()
        }
        txt2_VMList.setOnClickListener {
            onTextClick()
        }
    }
    fun getTime()
    {
        //Creating a TimePickerDialog
        val tpd = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                edt2_MTime.setText("$hourOfDay:$minute")
                sHour = hourOfDay
                sMin = minute
            },myCalendar.get(Calendar.HOUR),myCalendar.get(Calendar.MINUTE),true)
        tpd.show()  //To actually show it on clicking
    }
    fun onButtonClick()
    {
        u2_MedName = edt2_MName.text.toString()
        if(edt2_MName.text.toString() == "" || edt2_MTime.text.toString() == "")
        {
            Toast.makeText(this,"Form Incomplete",Toast.LENGTH_SHORT).show()
        }
        else
        {
            val token: String = refMed.push().key.toString() //Create a random token ID
            val model = Model(token,u2_MedName,sHour,sMin,"",u2_Name) //Creating an object of model class
            refMed.child(token).setValue(model).addOnCompleteListener { status -> //Adding data of type model at position: Token
                if(status.isSuccessful)
                {
                    Toast.makeText(this@UserProfile,"Medicine Successfully Added", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this@UserProfile,"Medicine Not Added", Toast.LENGTH_SHORT).show()
                }
            }
            //Resetting Edit Views
            edt2_MName.setText("")
            edt2_MTime.setText("")
        }

    }

    fun onTextClick()
    {
        val i = Intent(this@UserProfile,MedList::class.java)
        i.putExtra("uName",u2_Name)
        startActivity(i)
    }
}