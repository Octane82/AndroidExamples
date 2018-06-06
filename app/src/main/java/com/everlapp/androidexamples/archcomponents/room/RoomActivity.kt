package com.everlapp.androidexamples.archcomponents.room

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.everlapp.androidexamples.R
import kotlinx.android.synthetic.main.activity_room.*

/**
 * https://medium.com/mindorks/android-architecture-components-room-and-kotlin-f7b725c8d1d
 */
class RoomActivity : AppCompatActivity() {

    private var mDb: PersonDataBase? = null

    private lateinit var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDb = PersonDataBase.getInstance(this)

        val person = Person("Vasya", 27, "Green")
        val person2 = Person("Petya", 32, "Orange")

        // insertPersonDataToDb(person)
        // insertPersonDataToDb(person2)

        fetchPersonDataFromDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        PersonDataBase.destroyInstance()
        mDbWorkerThread.quit()
    }



    private fun insertPersonDataToDb(person: Person) {
        val task = Runnable { mDb?.personDataDao()?.insert(person) }
        mDbWorkerThread.postTask(task)
    }


    private fun fetchPersonDataFromDb() {
        val task = Runnable {
            val personData = mDb?.personDataDao()?.getAll()
            mUiHandler.post({
                if (personData == null || personData.size == 0) {
                    Toast.makeText(applicationContext, "Yay", Toast.LENGTH_LONG).show()
                } else {
                    // tvRoomMsg.text = "USERS size: ${personData.size}"
                    var personStrings: String = ""
                    for (person in personData) {
                        personStrings += person.name
                    }
                    tvRoomMsg.text = personStrings
                }
            })
        }

        Thread(task).start()

        //mDbWorkerThread.postTask(task)
    }


}