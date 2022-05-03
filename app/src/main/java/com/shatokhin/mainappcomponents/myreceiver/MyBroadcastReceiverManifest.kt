package com.shatokhin.mainappcomponents.myreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// данный класс создан для демонтрации работы BroadcastReceiver-а который НЕ регистрируется программно, а указывается только в Manifest-е
// данный BroadcastReceiver отображает Toast с наименованием action
class MyBroadcastReceiverManifest: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val text = "MyBroadcastReceiverManifest Getting broadcast:\nName action: ${intent.action}\n"
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

}