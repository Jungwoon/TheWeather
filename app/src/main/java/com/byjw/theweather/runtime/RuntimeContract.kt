package com.byjw.theweather.runtime

interface RuntimeContract {

    companion object {
        const val CODE_ALL = 100
        const val CODE_ACCESS_FINE_LOCATION = 200
        const val CODE_ACCESS_COARSE_LOCATION = 300
        const val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
        const val ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
    }

    interface View {

        fun grantAccessFineLocationPermission()

        fun grantAccessCoarseLocationPermission()

        fun grantAllPermissions()

        fun isClickDenyButton(permission: String): Boolean

    }

    interface Presenter {

        fun checkAccessFineLocationPermission(): Boolean

        fun checkAccessCoarseLocationPermission(): Boolean

        fun checkAllPermission(): Boolean

        fun grantFineLocationPermissions()

        fun grantFineCoarsePermissions()

        fun grantAllPermissions()

        fun isClickDenyButton(permission: String): Boolean

        fun goToSettings()
    }
}