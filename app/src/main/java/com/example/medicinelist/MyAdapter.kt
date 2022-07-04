package com.example.medicinelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter(var mCtx: Context, var customFile: Int, var arrDetails: ArrayList<Model>)
    : ArrayAdapter<Model>(mCtx,customFile,arrDetails)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInf: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInf.inflate(customFile,null)

        val txtView_mdcnName: TextView = view.findViewById(R.id.txtview_medicineName)
        txtView_mdcnName.setText(arrDetails[position].mName)

        val txtView_Time: TextView = view.findViewById(R.id.txtView_Time)
        txtView_Time.setText("Time: ${arrDetails[position].sHour}:${arrDetails[position].sMin}")

        val txtView_pName: TextView = view.findViewById(R.id.txtview_patientName)
        txtView_pName.setText(arrDetails[position].pName)
        return view
    }
}