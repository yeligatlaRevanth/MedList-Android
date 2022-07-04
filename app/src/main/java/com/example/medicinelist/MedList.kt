package com.example.medicinelist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import android.content.Intent
import androidx.appcompat.app.AlertDialog

class MedList : AppCompatActivity() {
    //Declaring View Variables
    lateinit var lstView: ListView
    lateinit var fabButton: FloatingActionButton
    lateinit var drawerLayout: DrawerLayout //The part that comes out
    lateinit var navView: NavigationView //nav_menu is linked in navView
    lateinit var toggle: ActionBarDrawerToggle //The Hamburger Icon

    //Variables
    var infoArray = ArrayList<Model>()

    //Firebase
    val userProfile = UserProfile()
    val database: FirebaseDatabase = userProfile.database
    val refMed: DatabaseReference = database.reference.child("Med")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_list)
        //Initializing Widgets
        lstView = findViewById(R.id.lstView)
        fabButton = findViewById(R.id.fab_addIcon)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)

        hamburgerIcon()
        floatingButton()
        navigationView()
        setNameinNavView()
        fbDataChange()
        onItemClickListener()
        onItemLongClickListener()
    }

    fun hamburgerIcon()
    {
        toggle = ActionBarDrawerToggle(this@MedList,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        //This gives the default icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //By default we get left arrow icon, so we use sync state
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return true
    }

    fun onItemClickListener()
    {
        val uName = intent.getStringExtra("uName").toString()

        lstView.setOnItemClickListener { parent, view, position, id ->
            if(infoArray[position].pName == uName)
            {
                val i = Intent(this@MedList, MedInfo::class.java)
                i.putExtra("medName",infoArray[position].mName)
                i.putExtra("medHour",infoArray[position].sHour)
                i.putExtra("medMin",infoArray[position].sMin)
                i.putExtra("medID",infoArray[position].id)
                i.putExtra("uDesc",infoArray[position].desc)
                startActivity(i)
            }
            else
            {
                Toast.makeText(this,"Access Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onItemLongClickListener()
    {
        lstView.setOnItemLongClickListener { parent, view, position, id ->
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Delete Record")
            alert.setMessage("Are you sure you want to delete the record?")
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                refMed.child(infoArray[position].id).removeValue()
            })
            alert.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            alert.create().show()
            true
        }
    }

    fun floatingButton()
    {
        fabButton.setOnClickListener {
            finish() //Returns to the activity which called current activty
        }
    }

    fun navigationView()
    {
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.myProfile -> {
                    finish()
                }
                R.id.privacy ->{
                    Toast.makeText(this@MedList,"Privacy Policy",Toast.LENGTH_SHORT).show()
                    val i = Intent(this@MedList,PrivacyPolicy::class.java)
                    startActivity(i)
                }
                R.id.addUser ->{
                    val i = Intent(this@MedList,MainScreen::class.java)
                    startActivity(i)
                    finish()
                }
            }
            true
        }
    }

    fun fbDataChange()
    {
        refMed.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                infoArray.clear()
                for (eachMed in snapshot.children) {
                    val med = eachMed.getValue(Model::class.java)
                    if (med != null) {
                        infoArray.add(med)
                    }
                }
                lstView.adapter = MyAdapter(this@MedList,R.layout.row,infoArray)
            }
            override fun onCancelled(error: DatabaseError){
            }
        })
    }

    fun setNameinNavView()
    {
        val uName = intent.getStringExtra("uName").toString()
        val headerView = navView.getHeaderView(0)
        val txt_headerName: TextView = headerView.findViewById(R.id.headertxt_Name)
        txt_headerName.text = uName
    }

}