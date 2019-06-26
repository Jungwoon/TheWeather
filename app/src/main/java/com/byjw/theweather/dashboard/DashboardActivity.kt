package com.byjw.theweather.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.byjw.theweather.R
import com.byjw.theweather.dashboard.dialog.AddCityDialog
import com.byjw.theweather.dashboard.adapter.DashboardViewAdapter
import com.byjw.theweather.runtime.RuntimeContract
import com.byjw.theweather.runtime.RuntimeContract.Companion.ACCESS_COARSE_LOCATION
import com.byjw.theweather.runtime.RuntimeContract.Companion.ACCESS_FINE_LOCATION
import com.byjw.theweather.runtime.RuntimeContract.Companion.CODE_ACCESS_COARSE_LOCATION
import com.byjw.theweather.runtime.RuntimeContract.Companion.CODE_ACCESS_FINE_LOCATION
import com.byjw.theweather.runtime.RuntimeContract.Companion.CODE_ALL
import com.byjw.theweather.runtime.RuntimePresenter
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : AppCompatActivity(), RuntimeContract.View {

    private lateinit var dashboardViewAdapter: DashboardViewAdapter
    private lateinit var dashboardPresenter: DashboardContract.Presenter
    private lateinit var runtimePresenter: RuntimeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runtimePresenter = RuntimePresenter(this, this)

        if (!runtimePresenter.checkAllPermission()) {
            runtimePresenter.grantAllPermissions()
        }

        dashboardViewAdapter = DashboardViewAdapter(this)

        this.card_recycler_view.setHasFixedSize(true)
        this.card_recycler_view.layoutManager = GridLayoutManager(this@DashboardActivity, 2)
        this.card_recycler_view.adapter = dashboardViewAdapter

        dashboardPresenter = DashboardPresenter(
            context = this,
            model = dashboardViewAdapter,
            view = dashboardViewAdapter
        )

        this.fab.setOnClickListener {
            dashboardPresenter.loadItems(runtimePresenter.checkAccessFineLocationPermission())
        }

        dashboardPresenter.loadItems(runtimePresenter.checkAccessFineLocationPermission())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                AddCityDialog(this, dashboardPresenter).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun grantAllPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
            CODE_ALL
        )
    }

    override fun grantAccessFineLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), CODE_ACCESS_FINE_LOCATION)
    }

    override fun grantAccessCoarseLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION), CODE_ACCESS_COARSE_LOCATION)
    }

    override fun isClickDenyButton(permission: String): Boolean {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
            return true
        return false
    }

}
