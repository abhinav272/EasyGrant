package com.abhinav.easygrantsample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.abhinav.easygrant.EasyGrantUtil
import com.abhinav.easygrant.GrantCallbacks
import com.abhinav.easygrant.PermissionRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GrantCallbacks {
    override fun onPermissionDenied(deniedPermissions: ArrayList<PermissionRequest>) {
        deniedPermissions.forEach { Log.e("denied", it.permissionName) }
    }

    override fun onPermissionDisabled(disabledPermissions: ArrayList<PermissionRequest>) {
        disabledPermissions.forEach { Log.e("disabled", it.permissionName) }
    }

    override fun onPermissionGranted(grantedPermissions: ArrayList<PermissionRequest>) {
        grantedPermissions.forEach { Log.e("granted", it.permissionName) }
    }


    val CAM_PERMISSION = 1
    private lateinit var permissions: ArrayList<String>
    private lateinit var rationalMessages: ArrayList<String>
    private lateinit var permissionsList: ArrayList<PermissionRequest>
    private var cameraPermission = PermissionRequest(Manifest.permission.CAMERA,
            "I need camera permission to show you world", 1)
    private var locationPermission = PermissionRequest(Manifest.permission.ACCESS_FINE_LOCATION,
            "I need location permission to find where are you", 2)
    private var smsPermission = PermissionRequest(Manifest.permission.READ_SMS,
            "I need SMS permission to read all SMS", 3)
    private var bodySensorPermission = PermissionRequest(Manifest.permission.BODY_SENSORS,
            "I need body sensor Permission", 4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissions = ArrayList()
        rationalMessages = ArrayList()
        permissions.add(Manifest.permission.CAMERA)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissions.add(Manifest.permission.READ_SMS)
        permissions.add(Manifest.permission.BODY_SENSORS)
        rationalMessages.add("Need Camera Permission for my use")
        rationalMessages.add("Need Location group permissions for my use")
        setContentView(R.layout.activity_main)
        btn_cam_permission.setOnClickListener {
            askPermission2()
        }
    }

    private fun askPermission2() {
        permissionsList = ArrayList()
        permissionsList.add(cameraPermission)
        permissionsList.add(locationPermission)
        permissionsList.add(bodySensorPermission)
        permissionsList.add(smsPermission)
        EasyGrantUtil.Builder()
                .withActivity(this)
                .withPermissions(permissionsList)
                .setCallback(this)
                .seek()
    }

    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Log.e("askPermission", "shouldShowRationale")
                    AlertDialog.Builder(this)
                            .setMessage("You need to give Cam Permission")
                            .setPositiveButton("OK", { dialog, which ->
                                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAM_PERMISSION)
                            })
                            .show()

                } else
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAM_PERMISSION)
            } else {
                Toast.makeText(applicationContext, "Cam Granted", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("RequestResult", "reqCode -> ${requestCode} , ${permissions[0]}, grantResult -> ${grantResults[0]}")
    }
}
