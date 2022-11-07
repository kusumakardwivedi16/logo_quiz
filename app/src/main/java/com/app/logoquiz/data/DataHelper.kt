package com.app.logoquiz.data

import com.app.logoquiz.data.db.Question
import com.app.logoquiz.data.network.QuestionsItem

object DataHelper {
    val questionList = mutableListOf<Question>()
    val alphabetsArr = listOf('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
        'R','S','T','U','V','W','X','Y','Z')

   /* val inputData = listOf(
        QuestionsItem("http://www.dsource.in/sites/default/files/resource/logo-design/logos/logos-representing-india/images/01.jpeg", "AADHAAR"),
        QuestionsItem("https://static.digit.in/default/thumb_101067_default_td_480x480.jpeg", "PHONEPE")
    )

    init {
        addToQuestionList(inputData)
    }

    fun addToQuestionList(questions: List<QuestionsItem>) {
        questions.forEach { questionList.add(fromDTO(it)) }
    }
    */


    fun fromDTO(item : QuestionsItem) : Question {
        val answer = item.name
        val validOptions = alphabetsArr - answer.toList().toSet()
        val options = mutableListOf<Char>()
        answer.forEach { options.add(it) }
        var index = 0
        while (options.size < 18) {
            if(options.contains(validOptions[index])) {
                index++;
            } else {
                options.add(validOptions[index])
            }
        }
        return Question(
            photoUrl = item.imgUrl,
            answer = item.name,
            options = options
        )
    }
}