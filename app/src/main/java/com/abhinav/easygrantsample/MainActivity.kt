package com.abhinav.easygrantsample

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abhinav.easygrant.EasyGrant
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

    private lateinit var permissionsList: ArrayList<PermissionRequest>

    /**
     * Create Permission Request
     * */
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
        setContentView(R.layout.activity_main)

        btn_cam_permission.setOnClickListener {
            askCamPermission()
        }

        btn_all_permission.setOnClickListener {
            askPermissionAll()
        }

        btn_location_permission.setOnClickListener {
            askLocationPermission()
        }

        btn_sms_permission.setOnClickListener {
            askSMSPermission()
        }
    }

    /**
     * Sample to use Single Permission
     * */
    private fun askCamPermission() {
        EasyGrant.Builder()
                .withActivity(this)
                .withPermission(cameraPermission)
                .setCallback(this)
                .seek()
    }

    private fun askSMSPermission() {
        EasyGrant.Builder()
                .withActivity(this)
                .withPermission(smsPermission)
                .setCallback(this)
                .seek()
    }

    private fun askLocationPermission() {
        EasyGrant.Builder()
                .withActivity(this)
                .withPermission(locationPermission)
                .setCallback(this)
                .seek()
    }

    /**
     * Sample to use Multiple Permission
     * */
    private fun askPermissionAll() {
        permissionsList = ArrayList()
        permissionsList.add(cameraPermission)
        permissionsList.add(locationPermission)
        permissionsList.add(bodySensorPermission)
        permissionsList.add(smsPermission)
        EasyGrant.Builder()
                .withActivity(this)
                .withPermissions(permissionsList)
                .setCallback(this)
                .seek()
    }
}
