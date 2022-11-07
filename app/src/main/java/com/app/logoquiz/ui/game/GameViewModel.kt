package com.app.logoquiz.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.logoquiz.data.db.Question
import com.app.logoquiz.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question> get() = _question

    private var _correctGuess = MutableLiveData<Pair<Int, Char>>()
    val correctGuess: LiveData<Pair<Int, Char>> get() = _correctGuess

    val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun getQuestionData(index: Int) {
        coroutineScope.launch {
            val data = repository.getQuestionData(index)

            _question.value = data
        }
    }

    fun checkContains(optionIndex: Int) {
        val data = _question.value

        data?.let {
            if (data.answer.contains(data.options[optionIndex])) {
                val ansIndex = data.answer.indexOf(data.options[optionIndex])
                _correctGuess.value = Pair(ansIndex, data.answer[ansIndex])
                Log.i("GAME","Option Index : $optionIndex, ansIndex :  $ansIndex " )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}