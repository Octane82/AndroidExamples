package com.everlapp.androidexamples.databinding

import androidx.databinding.ObservableField

data class User(
        val firstName: String = "",
        val lastName: String = "",
        val age: Int,

        val observableField: ObservableField<String> = ObservableField("Default string"),

        val observableName: ObservableField<String> = ObservableField("Default name")
)