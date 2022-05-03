package com.shatokhin.mainappcomponents.myservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.shatokhin.mainappcomponents.EXTRA_DATA_FOR_SERVICE
import com.shatokhin.mainappcomponents.TAG_FOR_LOGCAT

// существует 3 типа сервиса (Foreground, Background, Bound)
// в данном примере реализован Background сервис
// Сервисы нужны для выполнения длительных задач в фоновом режиме, они не предоставляют UI
// Из популярных приложений которые используют сервисы является VK, при прослушивании музыки используется сервис Foreground
// такой сервис имеет приоритеты по ресурсам
class MyBackgroundService() : Service(), Parcelable {

    // иммитируем беспрерывную работу сервиса
    private var isCancelled = false // флаг по которому узнаем о том что необходимо завершить работу сервиса

    private val myThread = Thread{

        while (!isCancelled){
            try {
                Log.d(TAG_FOR_LOGCAT, "service running")
                Thread.sleep(2000)
            }catch (e: InterruptedException){
                e.printStackTrace()
            }
        }

    }

    constructor(parcel: Parcel) : this() {
        isCancelled = parcel.readByte() != 0.toByte()
    }

    init {
        // выводим в лог момент инициализации объекта
        Log.d(TAG_FOR_LOGCAT, "service init")
    }

    override fun onCreate() {
        // выводим в лог момент создания сервиса
        Log.d(TAG_FOR_LOGCAT, "onCreate service")
        myThread.start()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // выводим в лог момент выполнения команды, которая запускается всякий раз когда вызывается startService()
        Log.d(TAG_FOR_LOGCAT, "service onStartCommand")

        // выводим в лог полученные данные из intent, если они есть
        val dataText = intent?.getStringExtra(EXTRA_DATA_FOR_SERVICE)
        dataText?.let {
            Log.d(TAG_FOR_LOGCAT, "adding data: $it")
        }

        return START_STICKY // если сервис был убит системой, то перезапускаем
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        // прекращаем работу сервиса установив соответствующий флаг
        // выводим в лог момент уничтожения сервиса
        isCancelled = true
        Log.d(TAG_FOR_LOGCAT, "onDestroy service")
        super.onDestroy()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isCancelled) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyBackgroundService> {
        override fun createFromParcel(parcel: Parcel): MyBackgroundService {
            return MyBackgroundService(parcel)
        }

        override fun newArray(size: Int): Array<MyBackgroundService?> {
            return arrayOfNulls(size)
        }
    }
}