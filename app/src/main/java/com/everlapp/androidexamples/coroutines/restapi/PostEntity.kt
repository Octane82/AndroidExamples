package com.everlapp.androidexamples.coroutines.restapi

data class PostEntity(
        val userId: Long,
        val id: Long,
        val title: String,
        val body: String
)