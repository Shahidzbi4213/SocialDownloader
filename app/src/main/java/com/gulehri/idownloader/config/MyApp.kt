package com.gulehri.idownloader.config

import android.app.Application
import com.gulehri.idownloader.networking.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Created by Shahid Iqbal on 10/6/2022.

class MyApp : Application() {

    companion object{
        val instagramApi: Api =
            Retrofit.Builder()
                .baseUrl(ApiConfig.Instagram_Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api::class.java)


        val facebookApi: Api =
            Retrofit.Builder()
                .baseUrl(ApiConfig.Facebook_Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api::class.java)

    }
}