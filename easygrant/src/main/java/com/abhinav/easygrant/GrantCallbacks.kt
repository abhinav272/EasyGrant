package com.abhinav.easygrant

/**
 * Created by abhinav.sharma on 29/11/17.
 */
public interface GrantCallbacks {
    /**
     * Ask permission on this Callback
     * */
    fun onPermissionRequired()

    /**
     * Show appropriate message on this Callback - permission denied
     * */
    fun onPermissionDenied()

    /**
     * Permission denied and Never show again checked
     * */
    fun onPermissionDisabled()

    /**
     * Permission granted on this Callback - do your stuff
     * */
    fun onPermissionGranted()

    /**
     * Rationale needed on this Callback - show explanation
     * Set up Rationale dialog why this permission is required
     * */
    fun onRationaleNeeded()
}