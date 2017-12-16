package com.abhinav.easygrant

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by abhinav.sharma on 15/12/17.
 */
class PermissionRequest(
        var permissionName: String,
        var permissionRationale: String,
        var permissionId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(permissionName)
        parcel.writeString(permissionRationale)
        parcel.writeInt(permissionId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PermissionRequest> {
        override fun createFromParcel(parcel: Parcel): PermissionRequest {
            return PermissionRequest(parcel)
        }

        override fun newArray(size: Int): Array<PermissionRequest?> {
            return arrayOfNulls(size)
        }
    }
}
