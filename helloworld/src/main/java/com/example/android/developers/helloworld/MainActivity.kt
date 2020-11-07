package com.example.android.developers.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView : TextView = findViewById(R.id.first_activity_text_view)
        first_activity_text_view.setOnClickListener {
            openSecondActivity()
        }
    }

    private fun openSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        val transmittedString = "string to transmit"
        intent.putExtra(SecondActivity.TRANSMITTED_STRING , transmittedString)

        val transmittedInt = 12
        intent.putExtra(SecondActivity.TRANSMITTED_INT,transmittedInt)

        val transmittedBoolean = false
        intent.putExtra(SecondActivity.TRANSMITTED_BOOLEAN, transmittedBoolean)


        startActivity(intent)
    }
}