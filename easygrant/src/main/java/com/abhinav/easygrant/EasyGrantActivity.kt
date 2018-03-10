package com.abhinav.easygrant

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import java.util.ArrayList

/**
 * Created by abhinav.sharma on 30/11/17.
 */
internal class EasyGrantActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private var alreadyGrantedPermissions: ArrayList<PermissionRequest> = ArrayList()
    private var alreadyDeniedPermissions: ArrayList<PermissionRequest> = ArrayList()
    private var disabledPermissions: ArrayList<PermissionRequest> = ArrayList()
    private var rationaleNeededPermissions: ArrayList<PermissionRequest> = ArrayList()
    private var needPermissions: ArrayList<PermissionRequest> = ArrayList()

    private var permissionRequest: PermissionRequest? = null
    private var multiplePermissionsRequest: ArrayList<PermissionRequest>? = null
    private var permissionMap: HashMap<String, PermissionRequest> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        permissionRequest = intent.getParcelableExtra("single_permission")
        multiplePermissionsRequest = intent.getParcelableArrayListExtra("multiple_permission")

        if (permissionRequest != null) {
            seekSinglePermission(permissionRequest!!)
            permissionMap.put(permissionRequest!!.permissionName, permissionRequest!!)
        }

        if (multiplePermissionsRequest != null)
            prepareList(multiplePermissionsRequest!!)

    }

    private fun seekSinglePermission(permissionRequest: PermissionRequest) {
        when {
            shouldAskPermission(permissionRequest.permissionName) -> {
                when {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRequest.permissionName)
                    -> createRationale(permissionRequest)
                    else -> getPermission(permissionRequest.permissionName)
                }
            }
            else -> permissionAlreadyGranted(permissionRequest)
        }
    }

    private fun prepareList(multiplePermissionsRequest: ArrayList<PermissionRequest>) {
        for (i in multiplePermissionsRequest.indices) {
            permissionMap.put(multiplePermissionsRequest[i].permissionName, multiplePermissionsRequest[i])
            if (shouldAskPermission(multiplePermissionsRequest[i].permissionName)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, multiplePermissionsRequest[i].permissionName))
                    rationaleNeededPermissions.add(multiplePermissionsRequest[i])
                else needPermissions.add(multiplePermissionsRequest[i])
            } else alreadyGrantedPermissions.add(multiplePermissionsRequest[i])
        }

        if (needPermissions.size > 0)
            seekMultiplePermissions(needPermissions)
        else if (rationaleNeededPermissions.size > 0) {
            createRationaleForMultiple(rationaleNeededPermissions)
        } else {
            EasyGrantUtil.onPermissionResult(alreadyGrantedPermissions, alreadyDeniedPermissions, disabledPermissions)
            finish()
        }
    }

    private fun shouldAskPermission() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private fun shouldAskPermission(permission: String): Boolean {
        if (shouldAskPermission()) {
            return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
        }
        return false
    }

    fun seekMultiplePermissions(multiplePermissionsRequest: ArrayList<PermissionRequest>) {
        var permissions = ArrayList<String>()
        multiplePermissionsRequest.forEach { permissions.add(it.permissionName) }
        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 2727)
    }

    fun seekMultiplePermissionsByRationale(multiplePermissionsRequest: ArrayList<PermissionRequest>) {
        var permissions = ArrayList<String>()
        multiplePermissionsRequest.forEach { permissions.add(it.permissionName) }
        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 7272)
    }

    private fun permissionAlreadyGranted(permission: PermissionRequest) {
        alreadyGrantedPermissions.add(permission)
        EasyGrantUtil.onPermissionResult(alreadyGrantedPermissions, alreadyDeniedPermissions, disabledPermissions)
        finish()
    }

    private fun getPermission(permission: String) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
    }

    /**
     * @param dialog not used and can be replaced with _
     * @param which not used and can be replaced with _
     * */
    private fun createRationale(permission: PermissionRequest) {
        AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
                .setMessage(permission.permissionRationale)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    getPermission(permission.permissionName)
                })
                .show()
    }

    /**
     * temporary message creation
     * todo - refactor this method
     * */
    private fun createRationaleForMultiple(permissions: ArrayList<PermissionRequest>) {
        var messages = ""
        permissions.forEach { messages += it.permissionRationale + "\n" }
        AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle("Multiple Permissions Needed")
                .setMessage(messages)
                .setPositiveButton("OK", { dialog, which ->
                    seekMultiplePermissionsByRationale(permissions)
                })
                .show()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in permissions.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                alreadyGrantedPermissions.add(permissionMap[permissions[i]]!!)
            else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]))
                    disabledPermissions.add(permissionMap[permissions[i]]!!)
                else alreadyDeniedPermissions.add(permissionMap[permissions[i]]!!)
            }
        }

        when (requestCode) {
            2727 -> {
                EasyGrantUtil.onPermissionResult(alreadyGrantedPermissions, alreadyDeniedPermissions, disabledPermissions)
                if (rationaleNeededPermissions.size > 0)
                    createRationaleForMultiple(rationaleNeededPermissions)
                else finish()
            }

            else -> {
                EasyGrantUtil.onPermissionResult(alreadyGrantedPermissions, alreadyDeniedPermissions, disabledPermissions)
                finish()
            }
        }
    }
}