package com.abhinav.easygrant

import java.security.Permissions

/**
 * Created by abhinav.sharma on 29/11/17.
 */
public interface GrantCallbacks {
    /**
     * Show appropriate message on this Callback - permission denied
     * */
    fun onPermissionDenied(deniedPermissions: Array<String>)

    /**
     * Permission denied and Never show again checked
     * */
    fun onPermissionDisabled(disabledPermissions: Array<String>)

    /**
     * Permission granted on this Callback - do your stuff
     * */
    fun onPermissionGranted(grantedPermissions: Array<String>)
}