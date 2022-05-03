package com.shatokhin.mainappcomponents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var buttonBroadcastReceiver: Button
    private lateinit var buttonService: Button
    private lateinit var buttonContentProvider: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()

        initClickListenerButtonBroadcastReceiver()
        initClickListenerButtonService()
        initClickListenerButtonContentProvider()
    }


    private fun initButtons() {
        buttonBroadcastReceiver = findViewById(R.id.btnBroadcastReceiver)
        buttonService = findViewById(R.id.btnService)
        buttonContentProvider = findViewById(R.id.btnContentProvider)
    }

    private fun initClickListenerButtonBroadcastReceiver() {
        buttonBroadcastReceiver.setOnClickListener {
            val intent = Intent(this, BroadcastExampleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initClickListenerButtonService() {
        buttonService.setOnClickListener {
            val intent = Intent(this, ServiceExampleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initClickListenerButtonContentProvider() {
        buttonContentProvider.setOnClickListener {
            val intent = Intent(this, ContentProviderExampleActivity::class.java)
            startActivity(intent)
        }
    }
}