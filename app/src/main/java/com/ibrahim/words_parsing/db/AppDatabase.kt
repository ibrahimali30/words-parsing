package com.ibrahim.words_parsing.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibrahim.words_parsing.words_count_feature.data.source.local.WordsDao
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel

@Database(
    entities = [
        WordsUiModel::class
    ],
    version = 2 , exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDao

}

