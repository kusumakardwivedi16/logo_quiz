package com.app.logoquiz.data.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.logoquiz.R
import com.app.logoquiz.data.DataHelper
import com.app.logoquiz.data.network.Questions
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class PreloadCallback(private val context: Context, private val daoProvider: Provider<QuestionDao>): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val inputJson = context.resources.openRawResource(R.raw.inputdata)
            .bufferedReader().use { it.readText() }
        val inputObjects = Gson().fromJson(inputJson, Questions::class.java)

        CoroutineScope(Dispatchers.Default).launch {
            insertData(inputObjects)
        }
    }

    private suspend fun insertData(data: Questions) {
        data.let { questions ->
            questions.forEach {
                daoProvider.get().addQuestion(DataHelper.fromDTO(it))
            }
        }
    }
}