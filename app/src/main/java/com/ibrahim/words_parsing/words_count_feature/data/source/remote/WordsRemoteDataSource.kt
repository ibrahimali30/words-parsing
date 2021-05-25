package com.ibrahim.words_parsing.words_count_feature.data.source.remote

import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class WordsRemoteDataSource @Inject constructor(
    private val wordsApiService: WordsApiService
) {

     fun fetchWords(): Single<ResponseBody> {
       return wordsApiService.getWords()
     }

}