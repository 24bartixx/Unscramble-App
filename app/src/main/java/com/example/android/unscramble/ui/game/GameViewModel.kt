package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var _score = 0
    private var _currentWordCount = 0
    private val _currentScrambledWord = MutableLiveData<String>()
            private lateinit var currentWord: String
    private var usedWords: MutableList<String> = mutableListOf()

    val score: Int get() = _score
    val currentWordCount: Int get() = _currentWordCount
    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    // get and shuffle a word
    private fun getNextWord() {
        currentWord = allWordsList.random()
        if(currentWord in usedWords) getNextWord()

        val shuffledWord = currentWord.toCharArray()
        while(String(shuffledWord).equals(currentWord, false)) shuffledWord.shuffle()

        _currentScrambledWord.value = String(shuffledWord)
        usedWords.add(currentWord)
        _currentWordCount++
    }

    // check whether there are some words left
    fun isThereNextWord(): Boolean {
        return if (_currentWordCount<MAX_WORDS) {
            getNextWord()
            true
        }
            else false
    }

    //check if the user's guess is correct
    fun checkWord(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            _score += SCORE_INCREASE
            return true
        }
        return false
    }


    fun reinitializeData() {
        _score = 0
        _currentWordCount
        usedWords.clear()
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}