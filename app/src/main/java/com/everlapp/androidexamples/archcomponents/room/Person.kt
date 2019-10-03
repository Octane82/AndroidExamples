package com.everlapp.androidexamples.archcomponents.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person (@PrimaryKey var name: String,
                   @ColumnInfo(name = "age") var age: Int,
                   @ColumnInfo(name = "favouriteColor") var favouriteColor: String)

