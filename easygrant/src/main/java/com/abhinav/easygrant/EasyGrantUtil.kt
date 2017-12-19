package com.abhinav.easygrant

import java.util.ArrayList

/**
 * Created by abhinav.sharma on 19/12/17.
 */
internal class EasyGrantUtil {

    internal companion object {

        private lateinit var callback: GrantCallbacks

        internal fun setCallback(callbacks: GrantCallbacks){
            callback = callbacks
        }

        internal fun onPermissionResult(grantedPermissions: ArrayList<PermissionRequest>,
                                        deniedPermissions: ArrayList<PermissionRequest>,
                                        disabledPermissions: ArrayList<PermissionRequest>) {
            callback.onPermissionDenied(deniedPermissions)
            callback.onPermissionGranted(grantedPermissions)
            callback.onPermissionDisabled(disabledPermissions)
        }
    }
}