package com.shatokhin.mainappcomponents

import android.content.Intent
import android.content.Intent.ACTION_POWER_CONNECTED
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.shatokhin.mainappcomponents.myreceiver.MyBroadcastReceiverProg

// BroadcastReceiver (Широковещательный приемник) нужен приложению для того чтобы реагировать
// на сообщения отправленные операционной системой Android либо другими приложениями.


class BroadcastExampleActivity : AppCompatActivity() {
    private val buttonSendMyBroadcast: Button by lazy { findViewById(R.id.btnSendMyBroadcast) }
    private val tvReceivedBroadcasts: TextView by lazy { findViewById(R.id.tvReceivedBroadcasts) }
    private val btnClear: TextView by lazy { findViewById(R.id.btnClear) }
    private lateinit var showHistoryBroadcastManifest: MyBroadcastReceiverProg.ShowHistoryBroadcast
    private lateinit var myBroadcastReceiverProg: MyBroadcastReceiverProg
    private var historyBroadcast = StringBuilder()
    private var countBroadcast = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver_example)

        initMyBroadcastReceiverProg()
        initClickListenerButtonSendMyBroadcast()
        initClickListenerButtonClear()
    }


    private fun initMyBroadcastReceiverProg() {
        // Данная функция создает BroadcastReceiver программно
        // Создается объект MyBroadcastReceiverProg, в аргументы подается интерфейс с помощью которого
        // полученные сообщения сохраняются в историю и отображаются в tvReceivedBroadcasts
        // далее идет регистрация BroadcastReceiver при помощи registerReceiver() в таком случае объявление в manifest-е не нужно
        showHistoryBroadcastManifest = object : MyBroadcastReceiverProg.ShowHistoryBroadcast{
            override fun show(text: String) {
                historyBroadcast.append("${++countBroadcast}. $text")
                tvReceivedBroadcasts.text = historyBroadcast.toString()
            }
        }

        myBroadcastReceiverProg = MyBroadcastReceiverProg(showHistoryBroadcastManifest)
        val filter = IntentFilter(ACTION_POWER_CONNECTED).apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(MY_BROADCAST_ACTION)
        }
        registerReceiver(myBroadcastReceiverProg, filter)
    }


    private fun initClickListenerButtonSendMyBroadcast() {
        // здесь происходит отправка своего собственного широковещательного сообщения (action)
        buttonSendMyBroadcast.setOnClickListener {
            Intent().also { intent ->
                intent.action = MY_BROADCAST_ACTION
                intent.putExtra(MY_BROADCAST_MESSAGE, "my message")
                sendBroadcast(intent)
            }
        }
    }

    private fun initClickListenerButtonClear() {
        // кнопка btnClear очищает историю в TextView (tvReceivedBroadcasts)
        btnClear.setOnClickListener {
            historyBroadcast.clear()
            countBroadcast = 0
            tvReceivedBroadcasts.text = historyBroadcast.toString()
        }
    }

    override fun onDestroy() {
        // снятие с регистрации
        super.onDestroy()
        unregisterReceiver(myBroadcastReceiverProg)
    }

}