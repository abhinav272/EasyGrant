package com.abhinav.easygrant

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.abhinav.easygrant.exception.IllegalEasyGrantBuilderException
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

        fun onPermissionResult(grantedPermissions: ArrayList<PermissionRequest>,
                               deniedPermissions: ArrayList<PermissionRequest>,
                               disabledPermissions: ArrayList<PermissionRequest>) {
            callback.onPermissionDenied(deniedPermissions)
            callback.onPermissionGranted(grantedPermissions)
            callback.onPermissionDisabled(disabledPermissions)
        }
    }

    init {
        val intent = Intent(builder.activity, EasyGrantActivity::class.java)
        if (builder.permissionsRequest?.size == 1 && builder.permissionRequest == null)
            intent.putExtra("single_permission", builder.permissionsRequest!![0])
        if (builder.permissionRequest != null)
            intent.putExtra("single_permission", builder.permissionRequest)
        if (builder.permissionsRequest != null && builder.permissionsRequest!!.size > 1)
            intent.putParcelableArrayListExtra("multiple_permission", builder.permissionsRequest)
        callback = builder.callback!!
        builder.activity?.startActivity(intent)
    }

    class Builder {

        internal var activity: Activity? = null
        internal var permissionRequest: PermissionRequest? = null
        internal var permissionsRequest: ArrayList<PermissionRequest>? = null
        internal var callback: GrantCallbacks? = null

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

        fun setCallback(callback: GrantCallbacks): Builder {
            this.callback = callback
            return this
        }

        fun seek(): EasyGrantUtil {
            if (activity == null)
                throw IllegalEasyGrantBuilderException("Activity not set in builder")
            else if (callback == null)
                throw IllegalEasyGrantBuilderException("Callbacks not set in builder")
            else if (permissionRequest == null && permissionsRequest == null)
                throw IllegalEasyGrantBuilderException("No Permissions added in builder")

            return EasyGrantUtil(this)
        }

    }

}