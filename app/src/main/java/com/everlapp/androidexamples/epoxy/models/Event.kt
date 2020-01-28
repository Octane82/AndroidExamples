package com.everlapp.androidexamples.epoxy.models

data class Event(
        val id: Long = 0L,
        val image: String = "",
        val title: String = "",
        val description: String = "",
        val createdAt: Long = 0L
)