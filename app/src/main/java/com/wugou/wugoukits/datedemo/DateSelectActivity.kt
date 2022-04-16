package com.wugou.wugoukits.datedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wugou.dateselectview.DateSelectView
import com.wugou.wugoukits.R

class DateSelectActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ClassifyActivityVIP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_selector)

        val dateSelectView = findViewById<DateSelectView>(R.id.date_select_view)
        dateSelectView.setYear(2022)
    }
}