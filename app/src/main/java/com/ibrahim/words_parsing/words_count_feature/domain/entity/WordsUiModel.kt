package com.ibrahim.words_parsing.words_count_feature.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WordsUiModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var word: String = "0",
    var count: Int = 0,
)