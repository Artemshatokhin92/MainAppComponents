package com.shatokhin.mainappcomponents

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// ContentProvider нужны в случаях когда нужно открыть доступ к данным своего приложения для других приложений

// в данном примере реализована возможность ПОЛУЧЕНИЯ данных, которые предоставили другие приложения,
// а именно получение базы данных контактов предоставленной системой
class ContentProviderExampleActivity : AppCompatActivity() {
    private val buttonGetContact: Button by lazy { findViewById(R.id.btnGetContact) }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_example)

        buttonGetContact.setOnClickListener {
            buttonGetContact.setOnClickListener {
                // проверяем дано ли разрешение на чтение контактов, если нет то запрашиваем разрешение
                if( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ){
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 0)
                }

                // получаем таблицу с контактами с помощью запроса query()
                val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                val cursor = contentResolver.query(uri, null, null,null,null)

                if (cursor != null) {
                    // если cursor не null отображаем количество полученных строк в лог
                    Log.d(TAG_FOR_LOGCAT, "Getting row (contacts): ${cursor.count}")

                    // в эмуляторе нет контактов, для тестирования данной функции необходимо подключать физический смартфон
                    if (cursor.count == 0){
                        Log.d(TAG_FOR_LOGCAT, "You don't have any contacts, add contacts")
                    }


                    // здесь происходит работа с обычными курсорами как еслибы мы работали с SQLite
                    if(cursor.count > 0){ // если в полученной таблицы присутствуют строки (их больше 0)
                        while (cursor.moveToNext()){ // перемещаем курсов на следующую строку (сначало на первую)
                            // если строки кончились, то вернеться false и произойдет выход из цикла

                                // из строки получаем имя контакта в соответствующем столбце,
                                    // для этого пользуемся константами чтобы получить индекс нужного столбца
                            val contactName = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                                // тоже самое для получения номера контакта
                            val contactPhone = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER))

                            // выводим в лог полученные данные о контакте (имя, номер)
                            Log.d(TAG_FOR_LOGCAT, "contactName: $contactName , contactPhone: $contactPhone")
                            // цикл повторяется пока не пройдет все строки из таблицы (пока не выведим в лог все контакты)
                        }
                    }
                }

            }
        }
    }
}