package com.example.fitness2023.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness2023.adapters.ExerciseModel

class MainViewModel : ViewModel() { //10
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
}