package com.ibrahim.words_parsing.words_count_feature.data.source.remote

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface WordsApiService {

    @GET("/")
    fun getWords(): Single<ResponseBody>

}