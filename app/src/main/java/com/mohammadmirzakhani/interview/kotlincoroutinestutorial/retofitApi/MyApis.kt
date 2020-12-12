package com.mohammadmirzakhani.interview.kotlincoroutinestutorial.retofitApi

import com.mohammadmirzakhani.interview.kotlincoroutinestutorial.model.Comment
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MyApis {
    @GET("/comments")
    suspend fun getComments(): Response<List<Comment>>
//    fun getComments(): Call<List<Comment>>

}