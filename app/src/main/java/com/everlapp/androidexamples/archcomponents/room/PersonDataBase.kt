package com.everlapp.androidexamples.archcomponents.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = arrayOf(Person::class), version = 1)
abstract class PersonDataBase : RoomDatabase() {

    abstract fun personDataDao() : PersonDataDao


    companion object {
        private var INSTANCE: PersonDataBase? = null

        fun getInstance(context: Context) : PersonDataBase? {
            if (INSTANCE == null) {
                synchronized(PersonDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    PersonDataBase::class.java, "weather.db")
                            .build()
                }
            }
            return INSTANCE
        }


        fun destroyInstance() {
            INSTANCE = null
        }

    }


}