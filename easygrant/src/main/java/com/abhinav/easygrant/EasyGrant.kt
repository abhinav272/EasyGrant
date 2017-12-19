package com.abhinav.easygrant

import android.app.Activity
import android.content.Intent
import com.abhinav.easygrant.exception.IllegalEasyGrantBuilderException
import java.util.ArrayList

/**
 * Created by abhinav.sharma on 29/11/17.
 */
class EasyGrant(builder: Builder) {

    init {
        val intent = Intent(builder.activity, EasyGrantActivity::class.java)
        if (builder.permissionsRequest?.size == 1 && builder.permissionRequest == null)
            intent.putExtra("single_permission", builder.permissionsRequest!![0])
        if (builder.permissionRequest != null)
            intent.putExtra("single_permission", builder.permissionRequest)
        if (builder.permissionsRequest != null && builder.permissionsRequest!!.size > 1)
            intent.putParcelableArrayListExtra("multiple_permission", builder.permissionsRequest)
        EasyGrantUtil.setCallback(builder.callback!!)
        builder.activity?.startActivity(intent)
    }

    /**
     * Builder for Using EasyGrant
     * @param activity -- mandatory
     * @param callback -- mandatory
     * @throws IllegalEasyGrantBuilderException if activity or callbacks not set or permissions
     * not added.
     * */
    class Builder {

        internal var activity: Activity? = null
            private set
        internal var permissionRequest: PermissionRequest? = null
            private set
        internal var permissionsRequest: ArrayList<PermissionRequest>? = null
            private set
        internal var callback: GrantCallbacks? = null
            private set

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

        fun seek(): EasyGrant {
            if (activity == null)
                throw IllegalEasyGrantBuilderException("Activity not set in builder")
            else if (callback == null)
                throw IllegalEasyGrantBuilderException("Callbacks not set in builder")
            else if (permissionRequest == null && permissionsRequest == null)
                throw IllegalEasyGrantBuilderException("No Permissions added in builder")

            return EasyGrant(this)
        }

    }

}