package com.everlapp.androidexamples.coroutines.restapi

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface PostsRestEndpoint {

    @GET("/posts")
    fun getPosts() : Deferred<Response<List<PostEntity>>>

}