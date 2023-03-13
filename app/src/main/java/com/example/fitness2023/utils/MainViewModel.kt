package com.example.fitness2023.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness2023.adapters.ExerciseModel

class MainViewModel : ViewModel() { //10
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
    var preferences: SharedPreferences? = null //20
    var currentDay = 0 //20.7

    fun savePreferences(key: String, value: Int){ //20.1
        preferences?.edit()?.putInt(key, value)?.apply()
    }

    fun getExerciseCount(): Int{ //20.2
        return preferences?.getInt(currentDay.toString(), 0) ?: 0
    }
}