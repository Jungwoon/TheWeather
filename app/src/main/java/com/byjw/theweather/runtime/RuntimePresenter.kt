package com.byjw.theweather.runtime

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat

class RuntimePresenter(
        private val context: Context,
        private val view: RuntimeContract.View?) : RuntimeContract.Presenter {

    override fun checkAllPermission()
        = checkAccessCoarseLocationPermission()
            && checkAccessFineLocationPermission()

    override fun grantAllPermissions() {
        view?.grantAllPermissions()
    }

    override fun checkAccessCoarseLocationPermission()
            = checkPermission(RuntimeContract.ACCESS_COARSE_LOCATION)

    override fun grantFineCoarsePermissions() {
        view?.grantAccessCoarseLocationPermission()
    }

    override fun checkAccessFineLocationPermission()
        = checkPermission(RuntimeContract.ACCESS_FINE_LOCATION)

    override fun grantFineLocationPermissions() {
        view?.grantAccessFineLocationPermission()
    }

    override fun isClickDenyButton(permission: String): Boolean {
        return view?.isClickDenyButton(permission) ?: false
    }

    override fun goToSettings() {
        val uri = Uri.fromParts("package", context.packageName, null)
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(settingsIntent)
    }

    private fun checkPermission(permission: String)
            = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED


}
