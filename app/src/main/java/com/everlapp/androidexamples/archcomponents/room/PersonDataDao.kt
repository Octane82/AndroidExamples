package com.everlapp.androidexamples.archcomponents.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PersonDataDao {

    @Query("SELECT * FROM person")
    fun getAll() : List<Person>


    @Insert
    fun insert(person: Person)


    @Delete
    fun delete(person: Person)


}