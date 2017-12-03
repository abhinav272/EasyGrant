package com.abhinav.easygrant

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Created by abhinav.sharma on 29/11/17.
 */
public class EasyGrantUtil(builder: Builder) : GrantCallbacks {


    override fun onPermissionRequired() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionDenied() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionDisabled() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionGranted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRationaleNeeded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private lateinit var appContext: Context
        private lateinit var easyGrantActivity: EasyGrantActivity

        fun getInstance(context: Context) {
            appContext = context
        }

        fun onPermissionResult(grantedPermissions: Array<String>, deniedPermissions: Array<String>) {

        }
    }

    init {
        val intent = Intent(builder.activity, EasyGrantActivity::class.java)
        intent.putExtra("permissions", builder.permissions[0])
        builder.activity.startActivity(intent)
    }

    class Builder {

        lateinit var activity: Activity
        lateinit var permissions: Array<String>

        fun withActivity(activity: Activity): Builder {
            this.activity = activity
            return this
        }

        fun permissions(permissions: Array<String>): Builder {
            this.permissions = permissions
            return this
        }

        fun seek(): EasyGrantUtil = EasyGrantUtil(this)

    }

}