package com.abhinav.easygrant

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.ArrayList

/**
 * Created by abhinav.sharma on 29/11/17.
 */
public class EasyGrantUtil(builder: Builder) {

    companion object {
        private lateinit var appContext: Context
        private lateinit var easyGrantActivity: EasyGrantActivity
        private lateinit var callback: GrantCallbacks

        fun getInstance(context: Context) {
            appContext = context
        }

        fun onPermissionResult(grantedPermissions: Array<String>, deniedPermissions: Array<String>) {
            callback.onPermissionDenied(deniedPermissions)
            callback.onPermissionGranted(grantedPermissions)
        }
    }

    init {
        val intent = Intent(builder.activity, EasyGrantActivity::class.java)
        intent.putParcelableArrayListExtra("multiple_permission", builder.permissionsRequest)
        intent.putExtra("single_permission", builder.permissionRequest)
        callback = builder.callback
        builder.activity.startActivity(intent)
    }

    class Builder {

        internal lateinit var activity: Activity
        internal lateinit var permissionRequest: PermissionRequest
        internal lateinit var permissionsRequest: ArrayList<PermissionRequest>
        internal lateinit var callback: GrantCallbacks

        internal lateinit var rationalMessages: ArrayList<String>

        fun withActivity(activity: Activity): Builder {
            this.activity = activity
            return this
        }

        fun withPermission(permissionRequest: PermissionRequest): Builder {
            this.permissionRequest = permissionRequest
            return this
        }

        fun withPermissions(permissionsRequest: List<PermissionRequest>): Builder {
            this.permissionsRequest = permissionsRequest as ArrayList<PermissionRequest>
            return this
        }

        fun setCallback(callback: GrantCallbacks): Builder{
            this.callback = callback
            return this
        }

        fun seek(): EasyGrantUtil = EasyGrantUtil(this)

    }

}