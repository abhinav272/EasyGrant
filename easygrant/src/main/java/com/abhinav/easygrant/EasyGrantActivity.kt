package com.abhinav.easygrant

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import java.util.ArrayList

/**
 * Created by abhinav.sharma on 30/11/17.
 */
class EasyGrantActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private var alreadyGrantedPermissions: ArrayList<PermissionRequest> = ArrayList()
    private lateinit var alreadyDeniedPermissions: ArrayList<PermissionRequest>
    private var rationaleNeededPermissions: ArrayList<PermissionRequest> = ArrayList()
    private var needPermissions: ArrayList<PermissionRequest> = ArrayList()

    private var permissionRequest: PermissionRequest? = null
    private var multiplePermissionsRequest: ArrayList<PermissionRequest>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        permissionRequest = intent.getParcelableExtra("single_permission")
        multiplePermissionsRequest = intent.getParcelableArrayListExtra("multiple_permission")

        if (permissionRequest != null) {
            seekSinglePermission(permissionRequest!!)
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
//        rationaleNeededPermissions.clear()
//        needPermissions.clear()
//        alreadyGrantedPermissions.clear()

        for (i in multiplePermissionsRequest.indices) {
            if (shouldAskPermission(multiplePermissionsRequest[i].permissionName)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, multiplePermissionsRequest[i].permissionName))
                    rationaleNeededPermissions.add(multiplePermissionsRequest[i])
                else needPermissions.add(multiplePermissionsRequest[i])
            } else alreadyGrantedPermissions.add(multiplePermissionsRequest[i])
        }


    }

    private fun shouldAskPermission() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private fun shouldAskPermission(permission: String): Boolean {
        if (shouldAskPermission()) {
            return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
        }
        return false
    }

    fun seekMultiplePermissions(permission: List<String>) {
        ActivityCompat.requestPermissions(this, permission.toTypedArray(), 1)
    }

    private fun permissionAlreadyGranted(permission: PermissionRequest) {
        Log.e("alreadygrantedfor", permission.permissionName)
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
                .setPositiveButton("OK", { dialog, which ->
                    getPermission(permission.permissionName)
                })
                .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("RequestResult", "reqCode -> $requestCode , ${permissions[0]}, grantResult -> ${grantResults[0]}")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("PermissionActivity", "Destroy")
    }
}