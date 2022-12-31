package com.gulehri.idownloader.networking

import com.gulehri.idownloader.config.ApiConfig
import com.gulehri.idownloader.models.Facebook
import com.gulehri.idownloader.models.Instagram
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


// Created by Shahid Iqbal on 10/6/2022.

interface Api {

    @GET("/index/")
    suspend fun getInstaPost(
        @Query("url") url:String,
        @Header("X-RapidAPI-Key") key: String = ApiConfig.Instagram_Rapid_Key,
        @Header("X-RapidAPI-Host") host: String = ApiConfig.Instagram_Rapid_Host,
    ):Response<Instagram>


    @GET("main.php")
    suspend fun getFacebookPost(
        @Query("url") url:String,
        @Header("X-RapidAPI-Key") key: String = ApiConfig.Facebook_Rapid_Key,
        @Header("X-RapidAPI-Host") host: String = ApiConfig.Facebook_Rapid_Host,
    ):Response<Facebook>

}