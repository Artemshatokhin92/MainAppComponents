package com.shatokhin.mainappcomponents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.shatokhin.mainappcomponents.myservice.MyBackgroundService

// Сервисы нужны для выполнения длительных задач в фоновом режиме, они не предоставляют UI
// Из популярных приложений которые используют сервисы является VK, при прослушивании музыки используется сервис Foreground
// такой сервис имеет приоритеты по ресурсам
class ServiceExampleActivity : AppCompatActivity() {
    private lateinit var btnStartService: Button
    private lateinit var btnStopService: Button
    private lateinit var btnSendData: Button
    private lateinit var edtData: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_example)

        initEditText()
        initButton()
        initClickListenerButton()
    }

    private fun initEditText() {
        edtData = findViewById(R.id.edtData)
    }

    private fun initClickListenerButton() {
        // кнопка запускает сервис
        // происходит инициализация объекта MyBackgroundService, далее onCreate() onStartCommand()
        btnStartService.setOnClickListener {
            val intent = Intent(this, MyBackgroundService::class.java)
            startService(intent)
        }

        // кнопка останавливает сервис
        // отрабатывает функция onDestroy() и объект уничтожается
        btnStopService.setOnClickListener {
            val intent = Intent(this, MyBackgroundService::class.java)
            stopService(intent)
        }

        // кнопка отправляет данные в сервис
        // если сервис не был запущен, то он запуститься - произойдет инициализация объекта, далее onCreate() onStartCommand()
        // если сервис уже запущен, то выполниться одна функция onStartCommand()
        btnSendData.setOnClickListener {
            val intent = Intent(this, MyBackgroundService::class.java)
            intent.putExtra(EXTRA_DATA_FOR_SERVICE, edtData.text.toString())
            startService(intent)
        }
    }


    private fun initButton(){
        btnStartService = findViewById(R.id.btnStartService)
        btnStopService = findViewById(R.id.btnStopService)
        btnSendData = findViewById(R.id.btnSendData)
    }

    override fun onDestroy() {
        // сервисы должны останавливаться либо сами, через функцию stopSelf()
        // либо из вне через stopService(), что и делаю при уничтожении данной Activity
        val intent = Intent(this, MyBackgroundService::class.java)
        stopService(intent)
        super.onDestroy()
    }
}