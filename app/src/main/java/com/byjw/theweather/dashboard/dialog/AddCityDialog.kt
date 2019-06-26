package com.byjw.theweather.dashboard.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.byjw.theweather.R
import com.byjw.theweather.dashboard.DashboardContract


class AddCityDialog(context: Context, private val presenter: DashboardContract.Presenter) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutParamsWindow = WindowManager.LayoutParams()
        layoutParamsWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParamsWindow.dimAmount = 0.8f
        window!!.attributes = layoutParamsWindow

        setContentView(R.layout.dialog_add)

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.dialog_add_auto_complete_text_view)

        autoCompleteTextView.setAdapter(
            ArrayAdapter(
                context,
                android.R.layout.simple_dropdown_item_1line,
                presenter.getAutoCompleteCityList()
            )
        )

        val addButton = findViewById<Button>(R.id.add_dialog_btn_add_city)
        addButton.setOnClickListener {
            presenter.getWeatherResponseByCity(autoCompleteTextView.text.toString())
            dismiss()
        }

    }
}