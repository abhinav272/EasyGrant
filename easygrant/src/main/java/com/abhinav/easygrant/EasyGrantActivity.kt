package com.abhinav.easygrant

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager

/**
 * Created by abhinav.sharma on 30/11/17.
 */
class EasyGrantActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        seekPermission(this, intent.getStringExtra("permissions"))
    }



    private fun shouldAskPermission() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private fun shouldAskPermission(context: Context, permission: String): Boolean {
        if (shouldAskPermission()) {
            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
        }
        return false
    }

    fun seekPermission(context: Context, permission: String) {
        when {
            shouldAskPermission(context, permission) -> {
                when {
                    ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
                    -> createRationale(permission)
                    else -> getPermission(permission)
                }
            }
            else -> permissionAlreadyGranted(permission)
        }
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
        AlertDialog.Builder(this)
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