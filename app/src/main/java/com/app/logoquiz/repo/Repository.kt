package com.app.logoquiz.repo

import com.app.logoquiz.data.db.Question
import com.app.logoquiz.data.db.QuestionDao
import javax.inject.Inject

class Repository @Inject constructor(
    private val dao: QuestionDao
) {
    fun getQuestionData(index: Int): Question {
        //return DataHelper.questionList[index]

        return dao.getQuestions()[index]
    }
}