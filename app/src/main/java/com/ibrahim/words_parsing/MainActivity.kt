package com.ibrahim.words_parsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ibrahim.words_parsing.words_count_feature.presentation.viewmodel.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var wordsViewModel: WordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordsViewModel.getWords()
        
    }
}