package com.abhinav.easygrant

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

/**
 * Created by abhinav.sharma on 29/11/17.
 */
class EasyGrantUtil {

    companion object {
        private lateinit var appContext: Context

        fun getInstance(context: Context) {
            appContext = context
        }
    }

    private fun shouldAskPermission() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private fun shouldAskPermission(context: Context, permission: String): Boolean {
        if (shouldAskPermission()) {
            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
        }
        return false
    }

    public fun seekPermission(context: Context, permission: String, listener: GrantCallbacks){
        when{
            shouldAskPermission(context, permission) -> {
                when{
                    ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
                            -> listener.onRationaleNeeded()
                    else -> listener.onPermissionRequired()
                }
            }
        }
    }


}