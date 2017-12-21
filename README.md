# EasyGrant

<img alt="Logo" height="200px" 
src="https://raw.githubusercontent.com/abhinav272/EasyGrant/master/art/logo.png" />

EasyGrant is yet another Android Library to ease the process of asking Runtime Permissions.
Quoting Android official site -- Beginning in Android 6.0 (API level 23), users grant permissions to apps
while the app is running, not when they install the app. This approach streamlines the app install process, 
since the user does not need to grant permissions when they install or update the app. 
It also gives the user more control over the app's functionality; for example, a user could choose to give a camera
app access to the camera but not to the device location. The user can revoke the permissions at any time, 
by going to the app's Settings screen.

Actual code for seeking permissions, showing rationale and handling disabled permissions is little complex and tightly
coupled with ``Activity``.

HOW IT WORKS
------------

Firstly, create ``PermissionRequest``

```kotlin
private var cameraPermission = PermissionRequest(Manifest.permission.CAMERA,
            "I need camera permission to show you world", 1)
```

Secondly, call the method like this for single permission

```kotlin
private fun askCamPermission() {
        EasyGrant.Builder()
                .withActivity(this)
                .withPermission(cameraPermission)
                .setCallback(this)
                .seek()
    }
```

EasyGrant will provide simple callback of ``GrantCallbacks`` like

```kotlin
interface GrantCallbacks {
   
    fun onPermissionDenied(deniedPermissions: ArrayList<PermissionRequest>)

    fun onPermissionDisabled(disabledPermissions: ArrayList<PermissionRequest>)

    fun onPermissionGranted(grantedPermissions: ArrayList<PermissionRequest>)
}
```

EasyGrant seeks either a single permission or seeks multiple permissions in one go.
To seek a single permission you must call ``withPermission(permissionRequest: PermissionRequest)``,
To seek multiple permission you must call ``withPermissions(permissionsRequest: List<PermissionRequest>)``

``PermissionRequest`` takes permission name, rationale message to be shown and id.

EasyGrant throws ``IllegalEasyGrantBuilderException``
if ``with(activity: Activity)``not called or called with a null value.
If ``setCallback(callback: GrantCallbacks)`` is not called or called with null value.
If none of the permissions are requested.

### Multiple permission

```kotlin
EasyGrant.Builder()
        .withActivity(this)
        .withPermissions(permissionsList)
        .setCallback(this)
        .seek()
```

Usage
-----

### Dependency

Include the library in your app level ``build.gradle``

```groovy
dependencies{
    compile 'com.abhinavsharma:easygrant:0.0.4'
}
```

Do you want to contribute?
--------------------------

Feel free to add or suggest any useful feature to the library, I would be glad to improve it with your help.


<p align="center">
  <h3>Proudly :muscle: made in <b><a href="https://kotlinlang.org/">Kotlin</a></b></h3>
</p>

License
-------

    Copyright 2017 Abhinav Sharma

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

