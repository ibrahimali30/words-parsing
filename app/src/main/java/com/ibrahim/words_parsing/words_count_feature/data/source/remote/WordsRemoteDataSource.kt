package com.ibrahim.words_parsing.words_count_feature.data.source.remote

import io.reactivex.Single
import javax.inject.Inject

class WordsRemoteDataSource @Inject constructor(
    private val wordsApiService: WordsApiService
) {

     fun fetchWords(): Single<String> {
       return wordsApiService.getWords().map {
           it.string().toString()
       }
     }

}