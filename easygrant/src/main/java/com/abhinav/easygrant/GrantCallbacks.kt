package com.abhinav.easygrant

import java.util.ArrayList

/**
 * Created by abhinav.sharma on 29/11/17.
 */
interface GrantCallbacks {
    /**
     * Show appropriate message on this Callback - permission denied
     * */
    fun onPermissionDenied(deniedPermissions: ArrayList<PermissionRequest>)

    /**
     * Permission denied and Never show again checked
     * */
    fun onPermissionDisabled(disabledPermissions: ArrayList<PermissionRequest>)

    /**
     * Permission granted on this Callback - do your stuff
     * */
    fun onPermissionGranted(grantedPermissions: ArrayList<PermissionRequest>)
}