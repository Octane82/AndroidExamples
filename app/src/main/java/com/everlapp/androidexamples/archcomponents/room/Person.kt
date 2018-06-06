package com.everlapp.androidexamples.archcomponents.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "person")
data class Person (@PrimaryKey var name: String,
                   @ColumnInfo(name = "age") var age: Int,
                   @ColumnInfo(name = "favouriteColor") var favouriteColor: String)

