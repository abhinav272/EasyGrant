package com.abhinav.easygrant

import android.content.Context
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

    private var customTheme: Int = 0
    private lateinit var permissions: ArrayList<String>
    private var alreadyGrantedPermissions: ArrayList<String> = ArrayList()
    private lateinit var alreadyDeniedPermissions: ArrayList<String>
    private var rationaleNeededPermissions: ArrayList<String> = ArrayList()
    private var needPermissions: ArrayList<String> = ArrayList()

    private lateinit var permissionRequest: PermissionRequest
    private lateinit var multiplePermissionsRequest: ArrayList<PermissionRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        permissionRequest = intent.getParcelableExtra("single_permission")
        multiplePermissionsRequest = intent.getParcelableArrayListExtra("multiple_permission")
        prepareList()

        if (needPermissions.size != 0)
            seekPermission(needPermissions)

    }

    private fun prepareList() {
        for (i in permissions.indices) {
            if (shouldAskPermission(this, permissions[i])) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]))
                    rationaleNeededPermissions.add(permissions[i])
                else needPermissions.add(permissions[i])
            } else alreadyGrantedPermissions.add(permissions[i])
        }
    }

    private fun shouldAskPermission() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private fun shouldAskPermission(context: Context, permission: String): Boolean {
        if (shouldAskPermission()) {
            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
        }
        return false
    }

    fun seekPermission(permission: List<String>) {
//        when {
//            shouldAskPermission(context, permission) -> {
//                when {
//                    ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
//                    -> createRationale(permission)
//                    else -> getPermission(permission)
//                }
//            }
//            else -> permissionAlreadyGranted(permission)
//        }

        ActivityCompat.requestPermissions(this, permission.toTypedArray(), 1)
    }

    private fun permissionAlreadyGranted(permission: String) {
        Log.e("alreadygrantedfor", "$permission")
        finish()
    }

    private fun getPermission(permission: String) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
    }

    /**
     * @param dialog not used and can be replaced with _
     * @param which not used and can be replaced with _
     * */
    private fun createRationale(permission: String) {
        AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
                .setMessage("You need to give Permission for $permission")
                .setPositiveButton("OK", { dialog, which ->
                    getPermission(permission)
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