package com.wugou.wugoukits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.wugou.wugoukits.classifydemo.ClassifyActivity
import com.wugou.wugoukits.datedemo.DateSelectActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_to_classify).setOnClickListener {
            startActivity(Intent(this, ClassifyActivity::class.java))
        }

        findViewById<Button>(R.id.btn_to_date_selector).setOnClickListener {
            startActivity(Intent(this, DateSelectActivity::class.java))
        }
    }
}