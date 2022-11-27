package com.prodroid.reqpermissiondialogexample

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        val REQUEST_LOCATION_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnCheckPer.setOnClickListener {
            if(checkPermission()){
                Toast.makeText(this, "Permission Granted!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission not Granted!!", Toast.LENGTH_SHORT).show()
            }
        }

        btnReqPre.setOnClickListener {
            if(!checkPermission()){
                reqPermission()
            } else {
                Toast.makeText(this, "Permission already granted!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkPermission() : Boolean{

        val result = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)

        val result1 = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)

        return result==PackageManager.PERMISSION_GRANTED && result1==PackageManager.PERMISSION_GRANTED

    }

    fun reqPermission(){
        requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, READ_EXTERNAL_STORAGE), REQUEST_LOCATION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== REQUEST_LOCATION_CODE){

            val locResult = grantResults[0]==PackageManager.PERMISSION_GRANTED
            val storageResult = grantResults[1]==PackageManager.PERMISSION_GRANTED

            if(locResult && storageResult){
                //My Code to be Executed which is dependent on permission!!
                Toast.makeText(this, "Permission Granted Successfully!!", Toast.LENGTH_SHORT).show()
            } else {
                showDialog()
                Toast.makeText(this, "Permission not Granted!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showDialog(){
        AlertDialog.Builder(this)
            .setTitle("Allow Permission")
            .setMessage("We need this permission for this feature to be enabled!!")
            .setPositiveButton("Request Permissions"
            ) { p0, p1 -> reqPermission() }
            .setNegativeButton("Cancel"){a,b->

            }.create().show()
    }
}