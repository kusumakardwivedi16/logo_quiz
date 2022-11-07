package com.app.logoquiz.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(
    @PrimaryKey
    val photoUrl: String,
    val answer : String,
    val options : List<Char>
)