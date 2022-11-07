package com.app.logoquiz.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Question::class],
    version = 1
)
abstract class QuestionDatabase : RoomDatabase() {
    abstract val dao: QuestionDao
}