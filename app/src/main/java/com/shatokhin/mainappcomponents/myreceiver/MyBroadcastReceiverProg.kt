package com.shatokhin.mainappcomponents.myreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.shatokhin.mainappcomponents.MY_BROADCAST_MESSAGE

// данный класс создан для демонтрации работы BroadcastReceiver-а программно (без указания в Manifest-е)
class MyBroadcastReceiverProg(private val implShowHistoryBroadcast: ShowHistoryBroadcast): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // переменная в которую сохраняется наименование полученного action и сообщение из intent если оно присутствует
        val broadcast = StringBuilder()

        val mainText = "MyBroadcastReceiverProg getting broadcast:\n"
        broadcast.append(mainText) // присоединяем основную фразу

        val action = "Name action: ${intent.action}\n"
        broadcast.append(action) // присоединяем наименование action

        intent.getStringExtra(MY_BROADCAST_MESSAGE)?.apply {
            val messageFromIntent = "Message: $this \n"
            broadcast.append(messageFromIntent) // присоединяем сообщение из intent-а, если оно присутствует
        }

        implShowHistoryBroadcast.show(broadcast.toString()) // передаем готовое сообщение в activity для отображения в TextView
    }

    interface ShowHistoryBroadcast{
        fun show(text: String)
    }
}