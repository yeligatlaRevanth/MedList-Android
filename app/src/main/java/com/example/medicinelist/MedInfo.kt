package com.example.medicinelist

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MedInfo : AppCompatActivity() {
    //View Variables
    lateinit var edt3_MName: EditText
    lateinit var edt3_MTime: EditText
    lateinit var edt3_desc: EditText
    lateinit var btn3_submit: Button

    //Data Variables
    lateinit var medName: String
    var medHour: Int = -1
    var medMin: Int = -1
    lateinit var descString: String
    lateinit var medId: String

    val medList = MedList()

    val myCalendar = Calendar.getInstance()

    //Database
    val fb: FirebaseDatabase = medList.database
    var fbdb: DatabaseReference = fb.reference.child("Med")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_info)
        //Initializing Widgets
        edt3_MName = findViewById(R.id.edt3_MName)
        edt3_MTime = findViewById(R.id.edt3_MTime)
        edt3_desc = findViewById(R.id.edt3_desc)
        btn3_submit = findViewById(R.id.btn3_Submit)

        //Action Bar to go back
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Data from previous Activity
        medName = intent.getStringExtra("medName").toString()
        medHour = intent.getIntExtra("medHour", -1)
        medMin = intent.getIntExtra("medMin", -1)
        medId = intent.getStringExtra("medID").toString()
        descString = intent.getStringExtra("uDesc").toString()

        //Setting data to widgets on activity start
        edt3_MName.setText(medName)
        edt3_MTime.setText("${medHour}:${medMin}")
        edt3_desc.setText(descString)

        edt3_MTime.setOnClickListener {
            getTime()
        }

        btn3_submit.setOnClickListener {
            onButtonClick()
        }

    }

    fun getTime()
    {
        val tpd = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                edt3_MTime.setText("$hourOfDay:$minute")
                medHour = hourOfDay
                medMin = minute
                },myCalendar.get(Calendar.HOUR),myCalendar.get(Calendar.MINUTE),true)
        tpd.show()
    }

    fun onButtonClick()
    {
        medName = edt3_MName.text.toString()
        descString = edt3_desc.text.toString()

            //For updating in firebase dataase
        val userUpdate = mutableMapOf<String, Any>()
        userUpdate["id"] = medId
        userUpdate["mname"] = medName
        userUpdate["shour"] = medHour
        userUpdate["smin"] = medMin
        userUpdate["desc"] = descString
        fbdb.child(medId).updateChildren(userUpdate)
        Toast.makeText(this,"Task Successful",Toast.LENGTH_SHORT).show()
        finish()
    }
}