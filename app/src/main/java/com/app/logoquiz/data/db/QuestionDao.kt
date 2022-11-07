package com.app.logoquiz.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDao {

    @Insert
    suspend fun addQuestion(data: Question)

    @Query("SELECT * from question_table")
    fun getQuestions(): List<Question>
}