package com.everlapp.androidexamples.archcomponents.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDataDao {

    @Query("SELECT * FROM person")
    fun getAll() : List<Person>


    @Insert
    fun insert(person: Person)


    @Delete
    fun delete(person: Person)


}